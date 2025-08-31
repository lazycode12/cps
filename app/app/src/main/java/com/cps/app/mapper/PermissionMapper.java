package com.cps.app.mapper;

import com.cps.app.dto.PermissionDto;
import com.cps.app.model.Permission;

public class PermissionMapper {
	
	public static PermissionDto toDto(Permission p) {
		return new PermissionDto(p.getId(), p.getNom(), p.getDescription());
	}

}
