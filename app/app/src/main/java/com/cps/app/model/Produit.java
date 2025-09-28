package com.cps.app.model;

import jakarta.persistence.*;
import java.util.Objects;

import com.cps.app.enums.CategorieProduit;

/**
 * Entité représentant un produit / article dans l'inventaire d'un cabinet médical.
 */
@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CategorieProduit categorie;

    /**
     * Quantité actuelle en stock (unité dépend de la colonne 'unite', ex: pcs, boite, ml).
     */
    @Column(nullable = false)
    private Double quantite = 0.0;

    /**
     * Ex: "pcs", "boîte", "ml", "tube", "flacon"
     */
    @Column(nullable = false, length = 30)
    private String unite;

    /**
     * Quantité minimale avant réapprovisionnement (alerte).
     */
    private Double seuilReapprovisionnement;


    // --- Constructeurs ---
    public Produit() {}

    public Produit(String nom,
                   CategorieProduit categorie,
                   Double quantite,
                   String unite) {
        this.nom = nom;
        this.categorie = categorie;
        this.quantite = quantite == null ? 0.0 : quantite;
        this.unite = unite;
    }

    // --- Getters / Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public CategorieProduit getCategorie() { return categorie; }
    public void setCategorie(CategorieProduit categorie) { this.categorie = categorie; }

    public Double getQuantite() { return quantite; }
    public void setQuantite(Double quantite) { this.quantite = quantite; }

    public String getUnite() { return unite; }
    public void setUnite(String unite) { this.unite = unite; }

    public Double getSeuilReapprovisionnement() { return seuilReapprovisionnement; }
    public void setSeuilReapprovisionnement(Double seuilReapprovisionnement) {
        this.seuilReapprovisionnement = seuilReapprovisionnement;
    }


    // --- equals / hashCode / toString ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produit)) return false;
        Produit produit = (Produit) o;
        return Objects.equals(id, produit.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", categorie=" + categorie +
                ", quantite=" + quantite +
                ", unite='" + unite + '\'' +
                '}';
    }
}

