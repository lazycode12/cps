package com.cps.app.dto.response;

import java.time.LocalDate;

public record ActiviteResponse(
		Long id,
		String type,
		String statut,
		LocalDate date,
		double prix,
		String patient
		) {}
