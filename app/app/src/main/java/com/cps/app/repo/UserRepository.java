package com.cps.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

}
