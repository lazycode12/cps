package com.cps.app.dto.response;

import com.cps.app.model.Role;

public record UserDtoResponse(
		Long id,
		String nom,
		String prenom,
		String email,
		Role role
		) {}
