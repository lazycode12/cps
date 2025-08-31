package com.cps.app.dto.request;

import java.time.LocalDate;

public record PatientRequest(
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
