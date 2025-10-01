package com.cps.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cps.app.model.TypeConsultation;
import com.cps.app.repo.TypeConsultationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TypeConsultationService {

    private final TypeConsultationRepository repository;
    private LogEntryService logEntryService;

    public TypeConsultationService (TypeConsultationRepository repository, LogEntryService logEntryService) {
        this.repository = repository;
        this.logEntryService = logEntryService;
    }

    public List<TypeConsultation> findAll() {
        return repository.findAll();
    }

    public TypeConsultation findById(Long id) {
    	logEntryService.info("toutes les TypeConsultation sont récupérées", "TypeConsultationService");
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("TypeConsultation not found with id: " + id));
    }

    public TypeConsultation save(TypeConsultation typeConsultation) {
    	logEntryService.info("TypeConsultation a été créée avec succès", "TypeConsultationService");
        return repository.save(typeConsultation);
    }

    public TypeConsultation update(Long id, TypeConsultation updated) {
    	logEntryService.info("le TypeConsultation a été mise à jour avec succès avec id: "+id.toString(), "RoleService");
        return repository.findById(id)
                .map(tc -> {
                    tc.setLibelle(updated.getLibelle());
                    tc.setPrix(updated.getPrix());
                    return repository.save(tc);
                })
                .orElseThrow(() -> new RuntimeException("TypeConsultation not found with id " + id));
    }

    public void delete(Long id) {
    	logEntryService.info("le TypeConsultation a été supprimée avec succès avec id: "+id.toString(), "RoleService");
        if (!repository.existsById(id)) {
            throw new RuntimeException("TypeConsultation not found with id " + id);
        }
        repository.deleteById(id);
    }

    public boolean existsByLibelle(String libelle) {
        return repository.existsByLibelle(libelle);
    }
}
