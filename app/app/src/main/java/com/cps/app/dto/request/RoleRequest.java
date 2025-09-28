package com.cps.app.dto.request;

import java.util.List;

public record RoleRequest(
		String nom,
		String description,
		List<Long> permissions
		) {

}
