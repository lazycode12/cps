package com.cps.app.dto.response;

import java.util.List;

public record RoleResponse(
		Long id,
		String nom,
		String description,
		List<PermissionResponse> permissions
		) {

}
