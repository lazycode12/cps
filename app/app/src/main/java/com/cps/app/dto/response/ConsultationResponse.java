package com.cps.app.dto.response;

import java.time.LocalDate;

import com.cps.app.dto.RdvSummary;

public record ConsultationResponse(
	    Long id,
	    LocalDate dateDebut,
	    LocalDate dateFin,
	    PatientResponse patient,
	    RdvSummary rdv,
	    FactureResponse facture
	    ) {}
