package com.cps.app.dto.response;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public record LoginResponse(
		String token,
	    String username,
	    List<String> authorities
		) {

}
