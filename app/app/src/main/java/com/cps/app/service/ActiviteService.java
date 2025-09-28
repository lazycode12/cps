package com.cps.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.ActiviteRequest;
import com.cps.app.dto.response.ActiviteResponse;
import com.cps.app.mapper.ActivityMapper;
import com.cps.app.model.Activite;
import com.cps.app.model.Consultation;
import com.cps.app.repo.ActiviteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ActiviteService {

	private ActiviteRepository activiteRepository;
	private ConsultationService consultationService;
	
	private static final Logger logger = LoggerFactory.getLogger(ActiviteService.class);
	
	public ActiviteService(ActiviteRepository activiteRepository, ConsultationService consultationService) {
		super();
		this.activiteRepository = activiteRepository;
		this.consultationService = consultationService;
		logger.info("ActiviteService initialized");
	}
	public Activite getActiviteById(Long id) {
		 logger.debug("Searching for activity with ID: {}", id);
		return activiteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Activite not found with id: " + id));
	}
	
	public List<ActiviteResponse> getAllActivites(){
		return activiteRepository
				.findAll()
				.stream()
				.map(ActivityMapper::toActiviteResponse)
				.toList();
	}

	
	public ResponseEntity<ApiResponse<ActiviteResponse>> createActivite(ActiviteRequest activite) {
		Activite a = new Activite();
	    try {
	    	Consultation c = consultationService.getConsultationById(activite.consultationId());
	    	
	    	a.setType(activite.type());
	    	a.setStatut(activite.statut());
	    	a.setPrix(activite.prix());
	    	a.setDate(activite.date());
	    	a.setConsultation(c);
	    	
	    	
	        a = activiteRepository.save(a);
	        
	        ActiviteResponse activityDto = ActivityMapper.toActiviteResponse(a);
	        SuccessResponse<ActiviteResponse> successResponse = new SuccessResponse<>("L'activité a été créée avec succès", activityDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<ActiviteResponse> errorResponse = new ErrorResponse<>("Échec de la création de l'activité", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
		
	
	public ResponseEntity<ApiResponse<ActiviteResponse>> updateActivite(Activite body, Long id) {
		try {			
			Activite a = getActiviteById(id);
			a.setDate(body.getDate());
			a.setStatut(body.getStatut());
			a.setType(body.getType());
			
			activiteRepository.save(a);
			ActiviteResponse activityDto = ActivityMapper.toActiviteResponse(a);
	        SuccessResponse<ActiviteResponse> successResponse = new SuccessResponse<>("l'activité a été mise à jour avec succès", activityDto);
	        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<ActiviteResponse> errorResponse = new ErrorResponse<>("échec de la mise à jour de l'activité", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public ResponseEntity<ApiResponse<ActiviteResponse>> deleteActivite(Long id) {
		try {
		Activite a = getActiviteById(id);
		ActiviteResponse activityDto = ActivityMapper.toActiviteResponse(a);
		activiteRepository.delete(a);
        SuccessResponse<ActiviteResponse> successResponse = new SuccessResponse<>("l'activité a été supprimée avec succès", activityDto);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
        ErrorResponse<ActiviteResponse> errorResponse = new ErrorResponse<>("échec de la suppression de l'activité", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
}

