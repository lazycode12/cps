package com.cps.app.dto.request;

public record UserUpdateRequest(
	    String nom,
	    String prenom,
	    String email,
	    Long roleId) {}
