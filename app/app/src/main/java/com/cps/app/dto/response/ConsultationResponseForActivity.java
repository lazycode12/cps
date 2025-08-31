package com.cps.app.dto.response;

import java.time.LocalDate;

public record ConsultationResponseForActivity(
	    Long id,
	    LocalDate dateDebut,
	    LocalDate dateFin,
	    String patient
		) {

}
