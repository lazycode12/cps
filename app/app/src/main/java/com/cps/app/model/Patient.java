package com.cps.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cps.app.dto.request.PatientRequest;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nom", nullable = false, length = 50)
	private String nom;
	
	@Column(name = "prenom", nullable = false, length = 50)
	private String prenom;
	
	@Column(name = "date_naissance", nullable = false, length = 50)
	private LocalDate dateNaissance;
	
	@Column(name = "cin", nullable = false, length = 50)
	private String cin;
	
	@Column(name = "telephone", nullable = false, length = 50)
	private String telephone;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "adresse", unique = true, nullable = false)
	private String adresse;
	
	@Column(name = "sexe", nullable = false, length = 64)
	private String sexe;
	
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] photoData;
    
    
    @OneToMany(mappedBy="patient")
    private List<Consultation> consultations;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentMedical> medicalFiles = new ArrayList<>();

	public Patient() {
		super();
	}

	public Patient(PatientRequest req) {
	    this.nom = req.nom();
	    this.prenom = req.prenom();
	    this.dateNaissance = req.dateNaissance();
	    this.cin = req.cin();
	    this.telephone = req.telephone();
	    this.email = req.email();
	    this.adresse = req.adresse();
	    this.sexe = req.sexe();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public byte[] getPhotoData() {
		return photoData;
	}

	public void setPhotoData(byte[] photoData) {
		this.photoData = photoData;
	}
    
    
}
