package com.cps.app.dto;

import java.time.LocalDate;

public record RdvDto(
		Long id,
		String motif,
		String statut,
		LocalDate date,
		ConsultationDto consultation
		) {

}
