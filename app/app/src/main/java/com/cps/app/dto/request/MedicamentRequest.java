package com.cps.app.dto.request;

public record MedicamentRequest(
		String nom,
		int qte,
		String description
		) {

}
