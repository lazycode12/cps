package com.cps.app.dto.request;

import java.time.LocalDate;

import com.cps.app.enums.StatutActivite;
import com.cps.app.enums.TypeActivite;

public record ActiviteRequest(
		TypeActivite type,
		StatutActivite statut,
		LocalDate date,
		double prix,
		Long consultationId
		) {}
