package com.cps.app.dto.response;

public record MedicamentResponse(
		Long id,
		String nom,
		int qte,
		String description
		) {
}
