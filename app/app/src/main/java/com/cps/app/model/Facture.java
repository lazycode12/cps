package com.cps.app.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	
	
	@Column(name = "date_paiment", nullable = true, length = 50)
	private LocalDate datePaiment;
	
	@OneToOne
	@JoinColumn(name="consultation_id")
	private Consultation consultation;

	public Facture() {
		super();
	}

	public Facture(Double montant, LocalDate datePaiment) {
		super();
		this.montant = montant;
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

	public LocalDate getDatePaiment() {
		return datePaiment;
	}

	public void setDatePaiment(LocalDate datePaiment) {
		this.datePaiment = datePaiment;
	}
	
	
	
	
}
