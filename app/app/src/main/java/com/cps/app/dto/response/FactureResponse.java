package com.cps.app.dto.response;

import java.time.LocalDate;

import com.cps.app.dto.ConsultationSummary;
import com.cps.app.enums.FactureStatut;

public record FactureResponse(
		Long id,
		Double montant,
		FactureStatut statut,
		LocalDate datePaiment,
		ConsultationSummary consultation
		) {}
