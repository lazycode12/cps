package com.cps.app.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps.app.dto.request.UserRequest;
import com.cps.app.dto.request.UserUpdateRequest;
import com.cps.app.dto.response.UserDtoResponse;
import com.cps.app.mapper.UserMapper;
import com.cps.app.model.User;
import com.cps.app.repo.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
    private final UserRepository userRepository;
    private RoleService rs;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private LogEntryService logEntryService;
	
	public UserService(UserRepository userRepository, RoleService rs, LogEntryService logEntryService) {
		super();
		this.userRepository = userRepository;
		this.rs = rs;
		this.logEntryService = logEntryService;
	}



	public User getUserById(Long id){
		return this.userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
	}
	
	public List<UserDtoResponse> getAllUsers() {
		logEntryService.info("toutes les Users sont récupérées", "UserService");
		return userRepository
				.findAll()
				.stream()
				.map(UserMapper::toDto)
				.toList();
	}
	
	@Transactional
	public UserDtoResponse createUser(UserRequest user) {
		User u = new User();

		u.setPassword(encoder.encode(user.password()));
		u.setNom(user.nom());
		u.setPrenom(user.prenom());
		u.setEmail(user.email());
		u.setRole(rs.getRoleById(user.roleId()));
		
		User u2 = this.userRepository.save(u);
		logEntryService.info("User a été créée avec succès", "UserService");
		return UserMapper.toDto(u2);
	}
	
//	@Transactional
	public void updateUser(UserUpdateRequest user, Long id) {
		User u = getUserById(id);
		
		u.setNom(user.nom());
		u.setPrenom(user.prenom());
		u.setEmail(user.email());
		
		u.setRole(rs.getRoleById(user.roleId()));
		
		UserMapper.toDto(userRepository.save(u));
		logEntryService.info("User a été mise à jour avec succès avec id: "+id.toString(), "UserService");
	}
	
	public void deleteUser(Long id) {
		User u = getUserById(id);
		userRepository.delete(u);
		logEntryService.info("User a été supprimée avec succès avec id: "+id.toString(), "UserService");
	}
}
