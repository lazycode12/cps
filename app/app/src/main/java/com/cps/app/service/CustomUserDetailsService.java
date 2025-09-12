package com.cps.app.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cps.app.model.Role;
import com.cps.app.model.User;
import com.cps.app.repo.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()) // Use email as the username
                .password(user.getPassword())
                .authorities(getAuthorities(user.getRole()))
                .build();
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        // Add role as authority (prefix with ROLE_)
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getNom()));
        
        // Add all permissions as authorities
        if (role.getPermissions() != null) {
            role.getPermissions().forEach(permission -> 
                authorities.add(new SimpleGrantedAuthority(permission.getNom())));
        }
        
        return authorities;
    }
}
