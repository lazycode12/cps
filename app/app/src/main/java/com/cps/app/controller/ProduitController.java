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
import com.cps.app.model.Produit;
import com.cps.app.service.ProduitService;

@RestController
@RequestMapping("/produits")
public class ProduitController {

	@Autowired
	private ProduitService produitService;
	
	@GetMapping("")
	public List<Produit> getAllProduits(){
		return produitService.getAllProduits();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getUserById(@PathVariable Long id){
    	Produit produit = produitService.getProduitById(id);
    	return new ResponseEntity<>(produit, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> createUser(@RequestBody Produit produit){
    	try {
			produitService.createProduit(produit);
	        SuccessResponse<Void> successResponse = new SuccessResponse<>("produit a été créée avec succès", null);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
		} catch (Exception e) {
			ErrorResponse<Void> errorResponse = new ErrorResponse<>("Échec de la création de produit" + e, null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@RequestBody Produit produit, @PathVariable Long id){
    	try {
			produitService.updateProduit(produit, id);
			SuccessResponse<Void> successResponse = new SuccessResponse<>("produit a été mise à jour avec succès", null);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
		} catch (Exception e) {
			ErrorResponse<Void> errorResponse = new ErrorResponse<>("échec de la mise à jour de produit" + e.getMessage(), null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id){
    	try {
			produitService.deleteProduit(id);
			SuccessResponse<Void> successResponse = new SuccessResponse<>("produit a été supprimée avec succès", null);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
		} catch (Exception e) {
			ErrorResponse<Void> errorResponse = new ErrorResponse<>("échec de la suppression de produit" + e.getMessage(), null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
    }
}
