package com.cps.app.model;

import java.time.LocalDate;

import com.cps.app.enums.StatutActivite;
import com.cps.app.enums.TypeActivite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "activite")
public class Activite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 50)
	private TypeActivite type;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "statut", nullable = false, length = 50)
	private StatutActivite statut;
	
	@Column(name = "date", nullable = false, length = 50)
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name="consultation_id")
	private Consultation consultation;

	public Activite() {
		super();
	}

	public Activite(TypeActivite type, StatutActivite statut, LocalDate date) {
		super();
		this.type = type;
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

	public TypeActivite getType() {
		return type;
	}

	public void setType(TypeActivite type) {
		this.type = type;
	}

	public StatutActivite getStatut() {
		return statut;
	}

	public void setStatut(StatutActivite statut) {
		this.statut = statut;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}
