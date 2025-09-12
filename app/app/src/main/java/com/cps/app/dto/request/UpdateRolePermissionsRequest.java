package com.cps.app.dto.request;

import java.util.List;

import com.cps.app.model.Permission;

public record UpdateRolePermissionsRequest(
		Long id,
		String nom,
		String description,
		List<Permission> permissions
		) {}
