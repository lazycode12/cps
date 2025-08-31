package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

}
