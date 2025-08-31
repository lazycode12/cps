package com.cps.app.dto;

import java.time.LocalDate;

public record ConsultationSummary(
	    Long id,
	    LocalDate dateDebut,
	    LocalDate dateFin
		) {}
