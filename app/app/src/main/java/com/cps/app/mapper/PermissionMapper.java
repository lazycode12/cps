package com.cps.app.mapper;

import com.cps.app.dto.response.PermissionResponse;
import com.cps.app.model.Permission;

public class PermissionMapper {
	
	public static PermissionResponse toDto(Permission p) {
		return new PermissionResponse(p.getId(), p.getNom(), p.getDescription());
	}

}
