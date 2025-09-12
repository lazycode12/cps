package com.cps.app.mapper;

import com.cps.app.dto.response.RoleResponse;
import com.cps.app.model.Role;

public class RoleMapper {

	public static RoleResponse toDto(Role r) {
		return new RoleResponse(
				r.getId(),
				r.getNom(),
				r.getDescription(),
				r.getPermissions().stream().map(PermissionMapper::toDto).toList());
		}
}
