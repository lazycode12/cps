package com.cps.app.dto.request;

import java.util.List;

public record OrdonanceRequest(
		Long consultationId,
		List<Long> medicamentsIds,
		String description
		) {

}
