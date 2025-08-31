package com.cps.app.model;

import java.time.LocalDate;

import com.cps.app.enums.RdvStatus;

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
@Table(name = "rdv")
public class Rdv {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "motif", nullable = false, length = 50)
	private String motif;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "statut", nullable = false, length = 50)
	private RdvStatus statut;
	
	@Column(name = "date", nullable = false, length = 50)
	private LocalDate date;
	
	@OneToOne
	@JoinColumn(name="consultation_id")
	private Consultation consultation;

	public Rdv() {
		super();
	}

	public Rdv(String motif, RdvStatus statut, LocalDate date) {
		super();
		this.motif = motif;
		this.statut = statut;
		this.date = date;
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

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public RdvStatus getStatut() {
		return statut;
	}

	public void setStatut(RdvStatus statut) {
		this.statut = statut;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	
	
}
