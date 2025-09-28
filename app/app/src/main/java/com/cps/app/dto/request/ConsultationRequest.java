package com.cps.app.dto.request;

import java.time.LocalDate;

public record ConsultationRequest(
	    LocalDate dateDebut,
	    LocalDate dateFin,
	    Long patientId,
	    Long typeId
	    ) {}
