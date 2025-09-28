package com.cps.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_consultation")
public class TypeConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String libelle;

    private Double prix;

    // --- Constructeurs ---
    public TypeConsultation() {}

    public TypeConsultation(String libelle, Double prix) {
        this.libelle = libelle;
        this.prix = prix;
    }

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}

