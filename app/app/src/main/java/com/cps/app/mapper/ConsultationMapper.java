package com.cps.app.mapper;

import com.cps.app.dto.ConsultationSummary;
import com.cps.app.dto.response.ConsultationResponse;
import com.cps.app.dto.response.ConsultationResponseForActivity;
import com.cps.app.model.Consultation;

public class ConsultationMapper {
	
	public static ConsultationResponse toConsultationResponse(Consultation c) {
		return new ConsultationResponse(
				c.getId(),
				c.getDateDebut(),
				c.getDateFin(),
				c.getPatient() != null ? PatientMapper.toPatientResponse(c.getPatient()) : null,
				c.getRdv() != null ? RdvMapper.toRdvSummary(c.getRdv()) : null,
				c.getFacture() != null ? FactureMapper.toDto(c.getFacture()) : null
				);
	}
	
	public static ConsultationSummary toConsultationSummary(Consultation c) {
		return new ConsultationSummary(
				c.getId(),
				c.getDateDebut(),
				c.getDateFin()
				);
	}
	
	
	public static ConsultationResponseForActivity toConsultationResponseForActivity(Consultation c) {
		return new ConsultationResponseForActivity(
				c.getId(),
				c.getDateDebut(),
				c.getDateFin(),
				c.getPatient() != null ?  c.getPatient().getNom() + " " + c.getPatient().getPrenom() : null
				);
	}
}
