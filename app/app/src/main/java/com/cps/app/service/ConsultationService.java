package com.cps.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.response.ConsultationResponse;
import com.cps.app.dto.response.ConsultationResponseForActivity;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.AddActivitiesToConsultationRequest;
import com.cps.app.dto.request.ConsultationRequest;
import com.cps.app.mapper.ConsultationMapper;
import com.cps.app.model.Activite;
import com.cps.app.model.Consultation;
import com.cps.app.model.Patient;
import com.cps.app.model.TypeConsultation;
import com.cps.app.repo.ActiviteRepository;
import com.cps.app.repo.ConsultationRepository;
import com.cps.app.repo.RdvRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsultationService {
	
	private ConsultationRepository consultationRepository;
	private PatientService patientService;
	private RdvRepository rdvRepository;
	private ActiviteRepository activiteRepository;
	private TypeConsultationService typeConsultationService;
	
	public ConsultationService(ConsultationRepository consultationRepository, PatientService patientService,
			RdvRepository rdvRepository, ActiviteRepository activiteRepository, TypeConsultationService typeConsultationService) {
		super();
		this.consultationRepository = consultationRepository;
		this.patientService = patientService;
		this.rdvRepository = rdvRepository;
		this.activiteRepository = activiteRepository;
		this.typeConsultationService = typeConsultationService;
	}
	
	



	public ConsultationRepository getConsultationRepository() {
		return consultationRepository;
	}


	public Consultation getConsultationById(Long id) {
		return consultationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Consultation not found with id: " + id));
	}


	public List<ConsultationResponse> getAllConsultations() {
	    return consultationRepository
	            .findAll()
	            .stream()
	            .map(ConsultationMapper::toConsultationResponse)
	            .toList();
	}

	public List<ConsultationResponseForActivity> getAllConsultationsForActivity() {
	    return consultationRepository
	            .findAll()
	            .stream()
	            .map(ConsultationMapper::toConsultationResponseForActivity)
	            .toList();
	}

	
	public ResponseEntity<ApiResponse<ConsultationResponse>> createConsultation(ConsultationRequest req) {
	    try {	  
	    	
	        Consultation c = new Consultation();
	        Patient p = patientService.getPatientById(req.patientId());
	        TypeConsultation tc = typeConsultationService.findById(req.typeId());
	        
	        c.setDateDebut(LocalDate.now());
	        c.setDateFin(req.dateFin());
	        c.setPatient(p);
	        c.setType(tc);
	        
	        consultationRepository.save(c);
	        
	        ConsultationResponse ConsultationResponse = ConsultationMapper.toConsultationResponse(c);
	        SuccessResponse<ConsultationResponse> successResponse = new SuccessResponse<>("Consultation  a été créée avec succès", ConsultationResponse);
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	        
	    } catch (Exception e) {
	        ErrorResponse<ConsultationResponse> errorResponse = new ErrorResponse<>("Échec de la création de Consultation " + e, null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public void addActivities(AddActivitiesToConsultationRequest req) throws Exception {
	    if (req == null) {
	        throw new IllegalArgumentException("Request cannot be null");
	    }
	    
	    if (req.activiteIds() == null || req.activiteIds().isEmpty()) {
	        return;
	    }
		try {			
			
			List<Activite> activities = this.activiteRepository.findAllById(req.activiteIds());
			Consultation c = getConsultationById(req.consultationId());
			c.getActivities().addAll(activities);
			
			consultationRepository.save(c);
			
		}catch (Exception e) {
			throw new Exception("Failed to add activities", e);

		}
		
	}
		
	
	public ResponseEntity<ApiResponse<ConsultationResponse>> updateConsultation(ConsultationRequest req, Long id) {
		try {			
			Consultation c = getConsultationById(id);
			c.setDateDebut(req.dateDebut());
			c.setDateFin(req.dateFin());
			
			consultationRepository.save(c);
			ConsultationResponse ConsultationResponse = ConsultationMapper.toConsultationResponse(c);
	        SuccessResponse<ConsultationResponse> successResponse = new SuccessResponse<>("Consultation a été mise à jour avec succès", ConsultationResponse);
	        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<ConsultationResponse> errorResponse = new ErrorResponse<>("échec de la mise à jour de Consultation", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public ResponseEntity<ApiResponse<ConsultationResponse>> deleteConsultation(Long id) {
		try {
		Consultation c = getConsultationById(id);
		ConsultationResponse ConsultationResponse = ConsultationMapper.toConsultationResponse(c);
		
		if(c.getRdv() != null) {
			c.getRdv().setConsultation(null);
			rdvRepository.save(c.getRdv());
		}
		
		consultationRepository.delete(c);
		
        SuccessResponse<ConsultationResponse> successResponse = new SuccessResponse<>("Consultation a été supprimée avec succès", ConsultationResponse);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        
    } catch (Exception e) {
        ErrorResponse<ConsultationResponse> errorResponse = new ErrorResponse<>("échec de la suppression de Consultation", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
}
