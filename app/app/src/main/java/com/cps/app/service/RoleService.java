package com.cps.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cps.app.dto.RoleDto;
import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.CreateRoleRequest;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
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
	
	public List<RoleDto> getAllRoles(){
		return roleRepository
				.findAll()
				.stream()
				.map(RoleMapper::toDto)
				.toList();
	}

	
	public ResponseEntity<ApiResponse<RoleDto>> createRole(CreateRoleRequest request) {
		if(roleRepository.existsByNom(request.getNom())) {
			throw new RuntimeException("Role name already exists");
		}
	    try {	    	
	    	// Create new role
	    	Role role = new Role();
	    	role.setNom(request.getNom());
	    	role.setDescription(request.getDescription());
	    	
	        if (request.getPermissionIds() != null && !request.getPermissionIds().isEmpty()) {
	            List<Permission> permissions = permissionRepository.findAllById(request.getPermissionIds());
	            role.setPermissions(permissions);
	        }
	        
	        Role r = roleRepository.save(role);
	        RoleDto roleDto = RoleMapper.toDto(r);
	        SuccessResponse<RoleDto> successResponse = new SuccessResponse<>("le role  a été créée avec succès", roleDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<RoleDto> errorResponse = new ErrorResponse<>("Échec de la création de role", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
		
	
	public ResponseEntity<ApiResponse<RoleDto>> updateRole(Role body, Long id) {
		try {			
			Role r = getRoleById(id);
			r.setNom(body.getNom());
			r.setDescription(body.getDescription());
			
			roleRepository.save(r);
			RoleDto roleDto = RoleMapper.toDto(r);
	        SuccessResponse<RoleDto> successResponse = new SuccessResponse<>("le role a été mise à jour avec succès", roleDto);
	        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<RoleDto> errorResponse = new ErrorResponse<>("échec de la mise à jour de role"+e, null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public ResponseEntity<ApiResponse<RoleDto>> deleteRole(Long id) {
		try {
		Role r = getRoleById(id);
		RoleDto roleDto = RoleMapper.toDto(r);
		roleRepository.delete(r);
        SuccessResponse<RoleDto> successResponse = new SuccessResponse<>("le role a été supprimée avec succès", roleDto);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
        ErrorResponse<RoleDto> errorResponse = new ErrorResponse<>("échec de la suppression de role", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
	
	
	
}
