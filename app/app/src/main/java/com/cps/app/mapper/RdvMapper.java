package com.cps.app.mapper;

import com.cps.app.dto.RdvSummary;
import com.cps.app.dto.response.RdvResponse;
import com.cps.app.model.Rdv;

public class RdvMapper {
	
	public static RdvResponse toRdvResponse(Rdv r) {
		return new RdvResponse(
				r.getId(),
				r.getMotif(),
				r.getStatut().getDescription(),
				r.getDate(),
				r.getConsultation() != null ? ConsultationMapper.toConsultationSummary(r.getConsultation()) : null,
				r.getConsultation() != null ? r.getConsultation().getPatient().getNom() + " " + r.getConsultation().getPatient().getPrenom() : null
				);
	}
	
	public static RdvSummary toRdvSummary(Rdv r) {
		return new RdvSummary(
				r.getId(),
				r.getMotif(),
				r.getStatut().getDescription(),
				r.getDate());
	}
	
	
}
