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
import com.cps.app.model.TypeConsultation;
import com.cps.app.service.TypeConsultationService;

@RestController
@RequestMapping("/type-consultations")
public class TypeConsultationController {
	
	@Autowired
	private TypeConsultationService service;
	
	@GetMapping("")
    public List<TypeConsultation> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeConsultation> getById(@PathVariable Long id) {
        TypeConsultation t = service.findById(id);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<TypeConsultation>> create(@RequestBody TypeConsultation typeConsultation) {
        if (service.existsByLibelle(typeConsultation.getLibelle())) {
            return ResponseEntity.badRequest().build();
        }
        try {
        	TypeConsultation t = service.save(typeConsultation);
	        SuccessResponse<TypeConsultation> successResponse = new SuccessResponse<>("Type consultation a été créée avec succès", t);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        }catch (Exception e) {
	         ErrorResponse<TypeConsultation> errorResponse = new ErrorResponse<>("Échec de la création de Type consultation: " + e.getMessage(), null);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable Long id, @RequestBody TypeConsultation updated) {
        try {
        	service.update(id, updated);
        	SuccessResponse<Void> successResponse = new SuccessResponse<>("Type consultation a été mise à jour avec succès", null);
        	return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (RuntimeException e) {
	         ErrorResponse<Void> errorResponse = new ErrorResponse<>("échec de la mise à jour de Type consultation: " + e.getMessage(), null);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            SuccessResponse<Void> successResponse = new SuccessResponse<>("Type consultation a été supprimée avec succès", null);
        	return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        	
        } catch (RuntimeException e) {
        	ErrorResponse<Void> errorResponse = new ErrorResponse<>("échec de la suppression de Type consultation: " + e.getMessage(), null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
	
	

}
