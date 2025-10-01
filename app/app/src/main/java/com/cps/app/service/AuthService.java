package com.cps.app.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cps.app.configuration.JwtUtil;
import com.cps.app.dto.request.LoginRequest;
import com.cps.app.repo.UserRepository;


@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private LogEntryService logEntryService;
    
    

    public AuthService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
			JwtUtil jwtUtil, UserRepository userRepository, LogEntryService logEntryService) {
		super();
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
		this.logEntryService = logEntryService;
	}



	public String login(LoginRequest loginRequest) {
		 Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
		 
        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
         
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());
        logEntryService.info("tentative de connexion", "AuthService");
        return jwtUtil.generateToken(userDetails);
    }

}
