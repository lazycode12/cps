package com.cps.app.dto.request;

import java.time.LocalDate;

import com.cps.app.enums.RdvStatus;

public record RdvRequest(
	    String motif,
	    RdvStatus statut,
	    LocalDate date,
	    Long consultationId
	    ) {}
