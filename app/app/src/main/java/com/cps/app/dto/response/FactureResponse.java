package com.cps.app.dto.response;

import java.time.LocalDate;

import com.cps.app.dto.ConsultationSummary;

public record FactureResponse(
		Long id,
		Double montant,
		LocalDate datePaiment,
		ConsultationSummary consultation
		) {}
