package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	boolean existsByNom(String nom);
}
