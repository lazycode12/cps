package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.Facture;

public interface FactureRepository extends JpaRepository<Facture, Long> {

}
