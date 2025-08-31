package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}
