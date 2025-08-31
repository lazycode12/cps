package com.cps.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.DocumentMedical;

public interface DocumentMedicalRepository extends JpaRepository<DocumentMedical, Long> {

	List<DocumentMedical> findByPatientId(Long patientId);
}
