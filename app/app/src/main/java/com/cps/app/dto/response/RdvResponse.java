package com.cps.app.dto.response;

import java.time.LocalDate;

import com.cps.app.dto.ConsultationSummary;

public record RdvResponse(
	    Long id,
	    String motif,
	    String statut,
	    LocalDate date,
	    ConsultationSummary consultation,
	    String patient
	    ) {}
