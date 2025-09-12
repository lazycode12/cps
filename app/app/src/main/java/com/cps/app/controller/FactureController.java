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
import com.cps.app.dto.request.FactureRequest;
import com.cps.app.dto.response.FactureResponse;
import com.cps.app.mapper.FactureMapper;
import com.cps.app.model.Facture;
import com.cps.app.service.FactureService;

@RestController
@RequestMapping("/factures")
@PreAuthorize("hasAuthority('gestion-factures')")
public class FactureController {

	@Autowired
	private FactureService factureService;
	
	@GetMapping("")
	public List<FactureResponse> getAllFactures(){
		return factureService.getAllFactures();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<FactureResponse> getUserById(@PathVariable Long id){
    	Facture facture = factureService.getFactureById(id);
    	return new ResponseEntity<>(FactureMapper.toDto(facture), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<FactureResponse>> createUser(@RequestBody FactureRequest facture){
    	return factureService.createFacture(facture);
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FactureResponse>> updateUser(@RequestBody Facture facture, @PathVariable Long id){
    	return factureService.updateFacture(facture, id);
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<FactureResponse>> deleteUser(@PathVariable Long id){
    	return factureService.deleteFacture(id);
    }
}
