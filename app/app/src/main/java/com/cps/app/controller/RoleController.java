package com.cps.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.CreateRoleRequest;
import com.cps.app.dto.request.UpdateRolePermissionsRequest;
import com.cps.app.dto.response.RoleResponse;
import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.mapper.RoleMapper;
import com.cps.app.model.Role;
import com.cps.app.service.RoleService;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasAuthority('gestion-roles')")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@GetMapping("")
	public List<RoleResponse> getAllRoles(){
		return roleService.getAllRoles();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id){
    	Role role = roleService.getRoleById(id);
    	return new ResponseEntity<>(RoleMapper.toDto(role), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody CreateRoleRequest role){
    	return roleService.createRole(role);
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(@RequestBody Role role, @PathVariable Long id){
    	return roleService.updateRole(role, id);
    	
    }
    
    @PutMapping("updatepermissions")
    public ResponseEntity<ApiResponse<Void>> updateRolesPermissions(@RequestBody List <UpdateRolePermissionsRequest> rqs){
    	try {
    		roleService.updateRolesPermissions(rqs);
	        SuccessResponse<Void> successResponse = new SuccessResponse<>("les permissions des roles  a été modifiers avec succès", null);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    	}catch (Exception e) {
	        ErrorResponse<Void> errorResponse = new ErrorResponse<>("Échec de la mise a jours des permissions", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> deleteRole(@PathVariable Long id){
    	return roleService.deleteRole(id);
    }
}
