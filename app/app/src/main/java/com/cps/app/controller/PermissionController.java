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

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.request.PermissionRequest;
import com.cps.app.dto.response.PermissionResponse;
import com.cps.app.mapper.PermissionMapper;
import com.cps.app.model.Permission;
import com.cps.app.service.PermissionService;

@RestController
@RequestMapping("/permissions")
@PreAuthorize("hasAuthority('gestion-roles-permissions')")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	
	@GetMapping("")
	public List<PermissionResponse> getAllPermissions(){
		return permissionService.getAllPermissions();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id){
    	Permission permission = permissionService.getPermissionById(id);
    	return new ResponseEntity<>(PermissionMapper.toDto(permission), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> createPermission(@RequestBody PermissionRequest permission){
    	return permissionService.createPermission(permission);
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(@RequestBody Permission permission, @PathVariable Long id){
    	return permissionService.updatePermission(permission, id);
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> deletePermission(@PathVariable Long id){
    	return permissionService.deletePermission(id);
    }
}
