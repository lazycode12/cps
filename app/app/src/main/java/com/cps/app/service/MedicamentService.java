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

	public MedicamentService(MedicamentRepository medicamentRepository) {
		super();
		this.medicamentRepository = medicamentRepository;
	}
	
	public Medicament getMedicamentById(Long id) {
		return medicamentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Medicament not found with id: " + id));
	}
	
	public List<MedicamentResponse> geAlltMedicaments(){
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
        
    	return MedicamentMapper.toMedicamentResponse(m);
	    	
	}
	
	public MedicamentResponse updateMedicament(MedicamentRequest req, Long id) {
		Medicament m = getMedicamentById(id);
	    	
    	m.setNom(req.nom());
    	m.setQte(req.qte());
    	m.setDescription(req.description());

    	m = medicamentRepository.save(m);
        
    	return MedicamentMapper.toMedicamentResponse(m);
	    	
	}
	
	public void deleteMedicament(Long id) {
		Medicament m = getMedicamentById(id);
		medicamentRepository.delete(m);
	}

}
