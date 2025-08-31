package com.cps.app.mapper;

import com.cps.app.dto.RoleDto;
import com.cps.app.model.Role;

public class RoleMapper {

	public static RoleDto toDto(Role r) {
		return new RoleDto(
				r.getId(),
				r.getNom(),
				r.getDescription(),
				r.getPermissions().stream().map(PermissionMapper::toDto).toList());
		}
}
