package com.cps.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.UserUpdateRequest;
import com.cps.app.dto.request.UserRequest;
import com.cps.app.dto.response.UserDtoResponse;
import com.cps.app.mapper.UserMapper;
import com.cps.app.model.User;
import com.cps.app.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public List<UserDtoResponse> getAllusers(){
		return userService.getAllUsers();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id){
    	User user = userService.getUserById(id);
    	return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<UserDtoResponse> createUser(@RequestBody UserRequest user){
    	UserDtoResponse newUser = userService.createUser(user);
    	return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    
    @PutMapping("/{ido}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@RequestBody UserUpdateRequest user, @PathVariable Long ido){
    	try {
    		userService.updateUser(user, ido);
	        SuccessResponse<Void> successResponse = new SuccessResponse<>("utilisateur a été mise à jour avec succès", null);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    		
    	}catch(Exception e) {
	        ErrorResponse<Void> errorResponse = new ErrorResponse<>("Échec de la mise à jour de l'utilisateur", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    	}
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
    	userService.deleteUser(id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
