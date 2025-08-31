package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cps.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
