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

import com.cps.app.dto.RoleDto;
import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.CreateRoleRequest;
import com.cps.app.mapper.RoleMapper;
import com.cps.app.model.Role;
import com.cps.app.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@GetMapping("")
	public List<RoleDto> getAllRoles(){
		return roleService.getAllRoles();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id){
    	Role role = roleService.getRoleById(id);
    	return new ResponseEntity<>(RoleMapper.toDto(role), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<RoleDto>> createRole(@RequestBody CreateRoleRequest role){
    	return roleService.createRole(role);
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDto>> updateRole(@RequestBody Role role, @PathVariable Long id){
    	return roleService.updateRole(role, id);
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDto>> deleteRole(@PathVariable Long id){
    	return roleService.deleteRole(id);
    }
}
