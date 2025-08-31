package com.cps.app.mapper;

import com.cps.app.dto.response.PatientResponse;
import com.cps.app.model.Patient;

public class PatientMapper {

	public static PatientResponse toPatientResponse(Patient patient) {
		return new PatientResponse(
				patient.getId(),
				patient.getNom(),
				patient.getPrenom(),
				patient.getDateNaissance(),
				patient.getCin(),
				patient.getTelephone(),
				patient.getEmail(),
				patient.getAdresse(),
				patient.getSexe()
				);
				
	}
}
