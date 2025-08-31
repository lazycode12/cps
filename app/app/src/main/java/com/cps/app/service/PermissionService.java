package com.cps.app.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps.app.dto.PermissionDto;
import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.PermissionRequest;
import com.cps.app.mapper.PermissionMapper;
import com.cps.app.model.Permission;
import com.cps.app.model.Role;
import com.cps.app.repo.PermissionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PermissionService {
	
	private PermissionRepository permissionRepository;
	private RoleService roleService;
	
	
	public PermissionService(PermissionRepository permissionRepository, RoleService roleService) {
		this.permissionRepository = permissionRepository;
		this.roleService = roleService;
	}

	public Permission getPermissionById(Long id) {
		return permissionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Permission not found with id: " + id));
	}
	public List<PermissionDto> getAllPermissions(){
		return permissionRepository
				.findAll()
				.stream()
				.map(PermissionMapper::toDto)
				.toList();
	}

	
	@Transactional
	public ResponseEntity<ApiResponse<Void>> createPermission(PermissionRequest permission) {
	    try {
	    	Permission p = new Permission();
	    	
	    	p.setNom(permission.nom());
	    	p.setDescription(permission.description());
	    	
	    	p = permissionRepository.save(p);
	    	
	    	 
	        // Create a final reference for use in lambda
	        final Permission finalPermission = p;
	    	
	    	List<Role> roles = roleService.getRolesByIds(permission.selectedRolesIds());
	    	
	    	for (Role role : roles) {
	            if (role.getPermissions().stream().noneMatch(perm -> perm.getId().equals(finalPermission.getId()))) {
	                role.getPermissions().add(finalPermission);
	            }
	        
	    	}
	    	// Save all roles at once
	    	roleService.getRoleRepository().saveAll(roles);
	    	
	    	
	        SuccessResponse<Void> successResponse = new SuccessResponse<>("permission a été créée avec succès", null);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	    }catch (DataAccessException e) {
	        ErrorResponse<Void> errorResponse = new ErrorResponse<>("Erreur de base de données", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	    catch (Exception e) {
	        ErrorResponse<Void> errorResponse = new ErrorResponse<>("Échec de la création de permission", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
		
	
	public ResponseEntity<ApiResponse<PermissionDto>> updatePermission(Permission body, Long id) {
		try {			
			Permission p = getPermissionById(id);
			p.setNom(body.getNom());
			p.setDescription(body.getDescription());
			
			permissionRepository.save(p);
			PermissionDto permissionDto = PermissionMapper.toDto(p);
	        SuccessResponse<PermissionDto> successResponse = new SuccessResponse<>("permission a été mise à jour avec succès", permissionDto);
	        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<PermissionDto> errorResponse = new ErrorResponse<>("échec de la mise à jour de permission", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public ResponseEntity<ApiResponse<PermissionDto>> deletePermission(Long id) {
		try {
		Permission a = getPermissionById(id);
		PermissionDto permissionDto = PermissionMapper.toDto(a);
		permissionRepository.delete(a);
        SuccessResponse<PermissionDto> successResponse = new SuccessResponse<>("permission a été supprimée avec succès", permissionDto);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
        ErrorResponse<PermissionDto> errorResponse = new ErrorResponse<>("échec de la suppression de permission", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
}
