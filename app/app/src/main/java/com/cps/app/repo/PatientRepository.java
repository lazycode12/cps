package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
