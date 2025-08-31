package com.cps.app.dto.request;

import java.time.LocalDate;

import com.cps.app.enums.FactureStatut;

public record FactureRequest(
		Double montant,
		FactureStatut statut,
		LocalDate datePaiment,
		Long consultationId
		) {

}
