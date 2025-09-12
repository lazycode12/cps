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
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.response.ConsultationResponse;
import com.cps.app.dto.response.ConsultationResponseForActivity;
import com.cps.app.dto.request.AddActivitiesToConsultationRequest;
import com.cps.app.dto.request.ConsultationRequest;
import com.cps.app.mapper.ConsultationMapper;
import com.cps.app.model.Consultation;
import com.cps.app.service.ConsultationService;

@RestController
@RequestMapping("/consultations")
@PreAuthorize("hasAuthority('gestion-consultation')")
public class ConsultationController {
	
	@Autowired
	private ConsultationService consultationService;
	
	@GetMapping("")
	public List<ConsultationResponse> getAllConsultations(){
		return consultationService.getAllConsultations();
	}
	
	@GetMapping("foractivity")
	public List<ConsultationResponseForActivity> getAllConsultationsForActivity(){
		return consultationService.getAllConsultationsForActivity();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> getConsultationById(@PathVariable Long id){
    	Consultation Consultation = consultationService.getConsultationById(id);
    	return new ResponseEntity<>(ConsultationMapper.toConsultationResponse(Consultation), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<ConsultationResponse>> createConsultation(@RequestBody ConsultationRequest Consultation){
    	return consultationService.createConsultation(Consultation);	
    }
    
    @PostMapping("addactivities")
    public ResponseEntity<ApiResponse<Void>> addActivities(@RequestBody AddActivitiesToConsultationRequest req) throws Exception{
    	try {    		
    		consultationService.addActivities(req);
    		SuccessResponse<Void> successResponse = new SuccessResponse<>("activities  a été ajoute avec succès", null);
    		
    		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    	}catch (Exception e) {
	        ErrorResponse<Void> errorResponse = new ErrorResponse<>("Échec de l'ajout des activites " + e, null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    	}
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultationResponse>> updateConsultation(@RequestBody ConsultationRequest Consultation, @PathVariable Long id){
    	return consultationService.updateConsultation(Consultation, id);
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultationResponse>> deleteConsultation(@PathVariable Long id){
    	return consultationService.deleteConsultation(id);
    }
}
