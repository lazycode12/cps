package com.cps.app.dto.request;

import java.time.LocalDate;


public record FactureRequest(
		LocalDate datePaiment,
		Long consultationId
		) {

}
