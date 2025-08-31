package com.cps.app.dto;

import java.time.LocalDate;

import com.cps.app.enums.RdvStatus;

public record RdvSummary(
	    Long id,
	    String motif,
	    String statut,
	    LocalDate date
		) {

}
