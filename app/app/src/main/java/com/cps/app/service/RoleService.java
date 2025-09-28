package com.cps.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.RoleRequest;
import com.cps.app.dto.response.RoleResponse;
import com.cps.app.mapper.RoleMapper;
import com.cps.app.model.Permission;
import com.cps.app.model.Role;
import com.cps.app.repo.RoleRepository;
import com.cps.app.repo.PermissionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService {

	private RoleRepository roleRepository;
	private PermissionRepository permissionRepository;
	
	public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
		this.roleRepository = roleRepository;
		this.permissionRepository = permissionRepository;
	}
	
	


	public RoleRepository getRoleRepository() {
		return roleRepository;
	}




	public Role getRoleById(Long id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
	}
	
	public List<Role> getRolesByIds(List<Long> ids) {
		return roleRepository.findAllById(ids);
	}
	
	public List<RoleResponse> getAllRoles(){
		return roleRepository
				.findAll()
				.stream()
				.map(RoleMapper::toDto)
				.toList();
	}

	
	public ResponseEntity<ApiResponse<RoleResponse>> createRole(RoleRequest req) {
		if(roleRepository.existsByNom(req.nom())) {
			throw new RuntimeException("Role name already exists");
		}
	    try {	    	
	    	// Create new role
	    	Role role = new Role();
	    	role.setNom(req.nom());
	    	role.setDescription(req.description());
	    	
	        if (req.permissions() != null && !req.permissions().isEmpty()) {
	            List<Permission> permissions = permissionRepository.findAllById(req.permissions());
	            role.setPermissions(permissions);
	        }
	        
	        Role r = roleRepository.save(role);
	        RoleResponse roleDto = RoleMapper.toDto(r);
	        SuccessResponse<RoleResponse> successResponse = new SuccessResponse<>("le role  a été créée avec succès", roleDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<RoleResponse> errorResponse = new ErrorResponse<>("Échec de la création de role", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
		
	
	public ResponseEntity<ApiResponse<RoleResponse>> updateRole(RoleRequest req, Long id) {
		try {			
			Role r = getRoleById(id);
			List<Permission> permissions = permissionRepository.findAllById(req.permissions());
			
			r.setNom(req.nom());
			r.setDescription(req.description());
			r.setPermissions(permissions);
			
			roleRepository.save(r);
			RoleResponse roleDto = RoleMapper.toDto(r);
	        SuccessResponse<RoleResponse> successResponse = new SuccessResponse<>("le role a été mise à jour avec succès", roleDto);
	        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<RoleResponse> errorResponse = new ErrorResponse<>("échec de la mise à jour de role"+e, null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public ResponseEntity<ApiResponse<RoleResponse>> deleteRole(Long id) {
		try {
		Role r = getRoleById(id);
		RoleResponse roleDto = RoleMapper.toDto(r);
		roleRepository.delete(r);
        SuccessResponse<RoleResponse> successResponse = new SuccessResponse<>("le role a été supprimée avec succès", roleDto);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
        ErrorResponse<RoleResponse> errorResponse = new ErrorResponse<>("échec de la suppression de role", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
	
	
	
}
