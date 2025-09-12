package com.cps.app.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cps.app.dto.request.LoginRequest;
import com.cps.app.dto.response.LoginResponse;
import com.cps.app.service.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	/**
	 * Authentifie un utilisateur et retourne un token JWT s'il est valide.
	 * @param loginRequest les informations d'authentification (email et mot de passe)
	 * @return ResponseEntity contenant le token JWT ou un message d'erreur si les identifiants sont invalides
	 */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
    	try {
    		
    		String token = authService.login(loginRequest);
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    		String username = authentication.getName();
    		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    		
    		// Convert to List<String>
    		List<String> authorityNames = authorities.stream()
    		        .map(GrantedAuthority::getAuthority)
    		        .toList();
    		
    		return ResponseEntity.ok(new LoginResponse(token, username, authorityNames));
    	}catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Authentication failed: " + e.getMessage(), null, null));
        }
    }
    
//    @GetMapping("/me")
//    public UserInfoResponse getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        
//        return new UserInfoResponse(username, authorities);
//    }
}
