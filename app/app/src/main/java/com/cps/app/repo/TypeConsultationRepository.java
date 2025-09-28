package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cps.app.model.TypeConsultation;

@Repository
public interface TypeConsultationRepository extends JpaRepository<TypeConsultation, Long> {
    boolean existsByLibelle(String libelle);
}
