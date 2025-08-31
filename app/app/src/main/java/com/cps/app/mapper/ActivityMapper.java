package com.cps.app.mapper;

import com.cps.app.dto.ActivityDto;
import com.cps.app.dto.response.ActiviteResponse;
import com.cps.app.model.Activite;

public class ActivityMapper {

//	public static ActivityDto toDto(Activite a) {
//		return new ActivityDto(a.getType(), a.getStatut(), a.getDate());
//	}
	public static ActiviteResponse toActiviteResponse(Activite a) {
		return new ActiviteResponse(
				a.getId(),
				a.getType().getLabel(),
				a.getStatut().getLabel(),
				a.getDate(),
				a.getConsultation().getPatient() != null ?  a.getConsultation().getPatient().getNom() + " " + a.getConsultation().getPatient().getPrenom() : null
				);
	}
}
