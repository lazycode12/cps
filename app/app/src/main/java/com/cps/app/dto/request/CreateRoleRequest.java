package com.cps.app.dto.request;

import java.util.List;

public class CreateRoleRequest {
	
    private String nom;
    private String description;
    private List<Long> permissionIds;
    
    // getters and setters
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Long> getPermissionIds() {
		return permissionIds;
	}
	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}
    

    
}