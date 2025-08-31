package com.cps.app.dto.response;

import java.time.LocalDate;

public record PatientResponse(
		Long id,
		String nom,
		String prenom,
		LocalDate dateNaissance,
		String cin,
		String telephone,
		String email,
		String adresse,
		String sexe
		) {

}
