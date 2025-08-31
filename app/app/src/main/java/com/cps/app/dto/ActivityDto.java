package com.cps.app.dto;

import java.time.LocalDate;

public record ActivityDto(
		String type,
		String statut,
		LocalDate date
		) {}
