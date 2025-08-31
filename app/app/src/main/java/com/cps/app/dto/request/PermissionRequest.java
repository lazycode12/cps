package com.cps.app.dto.request;

import java.util.List;

public record PermissionRequest(
		String nom,
		String description,
		List<Long> selectedRolesIds
		) {}
