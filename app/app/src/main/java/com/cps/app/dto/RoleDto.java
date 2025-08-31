package com.cps.app.dto;

import java.util.List;

public record RoleDto(
		Long id,
		String nom,
		String description,
		List<PermissionDto> permissions
		) {

}
