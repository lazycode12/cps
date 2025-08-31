package com.cps.app.dto.request;

public record UserRequest(
		String nom,
		String prenom,
		String email,
		Long roleId,
		String password
		) {}
