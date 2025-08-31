package com.cps.app.mapper;

import com.cps.app.dto.response.MedicamentResponse;
import com.cps.app.model.Medicament;

public class MedicamentMapper {

	public static MedicamentResponse toMedicamentResponse(Medicament m) {
		return new MedicamentResponse(
				m.getId(),
				m.getNom(),
				m.getQte(),
				m.getDescription()
				);
	}
}
