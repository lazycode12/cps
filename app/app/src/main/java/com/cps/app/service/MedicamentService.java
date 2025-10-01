package com.cps.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cps.app.dto.request.MedicamentRequest;
import com.cps.app.dto.response.MedicamentResponse;
import com.cps.app.mapper.MedicamentMapper;
import com.cps.app.model.Medicament;
import com.cps.app.repo.MedicamentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicamentService {
	
	private MedicamentRepository medicamentRepository;
	private LogEntryService logEntryService;

	public MedicamentService(MedicamentRepository medicamentRepository, LogEntryService logEntryService) {
		super();
		this.medicamentRepository = medicamentRepository;
		this.logEntryService = logEntryService;
	}
	
	public Medicament getMedicamentById(Long id) {
		return medicamentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Medicament not found with id: " + id));
	}
	
	public List<MedicamentResponse> geAlltMedicaments(){
		logEntryService.info("toutes les Medicaments sont récupérées", "MedicamentService");
		return medicamentRepository
				.findAll()
				.stream()
				.map(MedicamentMapper::toMedicamentResponse)
				.toList();
	}
	
	public MedicamentResponse createMedicament(MedicamentRequest req) {
		Medicament m = new Medicament();
	    	
    	m.setNom(req.nom());
    	m.setQte(req.qte());
    	m.setDescription(req.description());

    	m = medicamentRepository.save(m);
    	logEntryService.info("Medicament a été créée avec succès", "MedicamentService");
    	return MedicamentMapper.toMedicamentResponse(m);
	    	
	}
	
	public MedicamentResponse updateMedicament(MedicamentRequest req, Long id) {
		Medicament m = getMedicamentById(id);
	    	
    	m.setNom(req.nom());
    	m.setQte(req.qte());
    	m.setDescription(req.description());

    	m = medicamentRepository.save(m);
    	logEntryService.info("le Medicament a été mise à jour avec succès, id: "+id.toString(),"MedicamentService");
    	return MedicamentMapper.toMedicamentResponse(m);
	    	
	}
	
	public void deleteMedicament(Long id) {
		Medicament m = getMedicamentById(id);
		logEntryService.info("le Medicament a été supprimée avec succès id: "+id.toString(),"MedicamentService");
		medicamentRepository.delete(m);
	}

}
