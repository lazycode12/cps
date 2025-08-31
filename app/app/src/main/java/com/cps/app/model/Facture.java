package com.cps.app.model;

import java.time.LocalDate;

import com.cps.app.enums.FactureStatut;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "facture")
public class Facture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "montant", nullable = true, length = 50)
	private Double montant;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "statut", nullable = false, length = 50)
	private FactureStatut statut;
	
	@Column(name = "date_paiment", nullable = true, length = 50)
	private LocalDate datePaiment;
	
	@OneToOne
	@JoinColumn(name="consultation_id")
	private Consultation consultation;

	public Facture() {
		super();
	}

	public Facture(double montant, FactureStatut statut, LocalDate datePaiment) {
		super();
		this.montant = montant;
		this.statut = statut;
		this.datePaiment = datePaiment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public Consultation getConsultation() {
		return consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public FactureStatut getStatut() {
		return statut;
	}

	public void setStatut(FactureStatut statut) {
		this.statut = statut;
	}

	public LocalDate getDatePaiment() {
		return datePaiment;
	}

	public void setDatePaiment(LocalDate datePaiment) {
		this.datePaiment = datePaiment;
	}
	
	
	
	
}
