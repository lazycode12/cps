package com.cps.app.dto;

import java.time.LocalDate;


public record RdvSummary(
	    Long id,
	    String motif,
	    String statut,
	    LocalDate date
		) {

}
