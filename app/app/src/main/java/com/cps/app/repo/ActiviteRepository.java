package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.Activite;

public interface ActiviteRepository extends JpaRepository<Activite, Long> {

}
