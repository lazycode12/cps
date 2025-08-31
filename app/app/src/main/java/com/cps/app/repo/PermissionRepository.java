package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
