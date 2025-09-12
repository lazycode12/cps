package com.cps.app.dto.request;

import java.util.List;

public record AddActivitiesToConsultationRequest(
		Long consultationId,
		List<Long> activiteIds
		) {

}
