package com.cps.app.dto;

import java.time.LocalDate;

import com.cps.app.dto.response.FactureResponse;
import com.cps.app.dto.response.PatientResponse;

public record ConsultationDto(
		Long id,
		LocalDate dateDebut,
		LocalDate dateFin,
		PatientResponse patient,
		RdvSimpleDto rdv,
		FactureResponse facture
		) {}

final record RdvSimpleDto(
	    Long id,
	    String motif,
	    String statut,
	    LocalDate date
	    // No consultation reference to avoid circular reference
	) {}
