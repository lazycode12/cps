package com.cps.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultation")
public class Consultation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "date_debut", nullable = false, length = 50)
	private LocalDate dateDebut;
	
	@Column(name = "date_fin", nullable = false, length = 50)
	private LocalDate dateFin;
	
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;
	
	@OneToOne
	@JoinColumn(name="rdv_id", nullable = true)
	private Rdv rdv;
	
	@OneToOne
	@JoinColumn(name="facture_id", nullable = true)
	private Facture facture;
	
	@OneToMany(mappedBy="consultation")
	private List<Activite> activities = new ArrayList<>();

	public Consultation() {
		super();
	}

	public Consultation(LocalDate dateDebut, LocalDate dateFin) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	
	public List<Activite> getActivities() {
		return activities;
	}

	public void setActivities(List<Activite> activities) {
		this.activities = activities;
	}

	public Long getId() {
		return id;
	}

	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Rdv getRdv() {
		return rdv;
	}

	public void setRdv(Rdv rdv) {
		this.rdv = rdv;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	
	
	
}
