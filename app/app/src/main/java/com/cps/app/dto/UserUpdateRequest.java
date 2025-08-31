package com.cps.app.dto;

public record UserUpdateRequest(
	    String nom,
	    String prenom,
	    String email,
	    Long roleId) {}
