package com.cps.app.mapper;

import com.cps.app.dto.response.FactureResponse;
import com.cps.app.model.Facture;

public class FactureMapper {

	public static FactureResponse toDto(Facture f) {
		return new FactureResponse(
				f.getId(),
				f.getMontant(),
				f.getDatePaiment(),
				ConsultationMapper.toConsultationSummary(f.getConsultation())
				);
	}
}
