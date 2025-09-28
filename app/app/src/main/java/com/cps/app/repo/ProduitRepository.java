package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
