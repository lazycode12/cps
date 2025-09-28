package com.cps.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cps.app.model.TypeConsultation;
import com.cps.app.repo.TypeConsultationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TypeConsultationService {

    private final TypeConsultationRepository repository;

    public TypeConsultationService (TypeConsultationRepository repository) {
        this.repository = repository;
    }

    public List<TypeConsultation> findAll() {
        return repository.findAll();
    }

    public TypeConsultation findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("TypeConsultation not found with id: " + id));
    }

    public TypeConsultation save(TypeConsultation typeConsultation) {
        return repository.save(typeConsultation);
    }

    public TypeConsultation update(Long id, TypeConsultation updated) {
        return repository.findById(id)
                .map(tc -> {
                    tc.setLibelle(updated.getLibelle());
                    tc.setPrix(updated.getPrix());
                    return repository.save(tc);
                })
                .orElseThrow(() -> new RuntimeException("TypeConsultation not found with id " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("TypeConsultation not found with id " + id);
        }
        repository.deleteById(id);
    }

    public boolean existsByLibelle(String libelle) {
        return repository.existsByLibelle(libelle);
    }
}
