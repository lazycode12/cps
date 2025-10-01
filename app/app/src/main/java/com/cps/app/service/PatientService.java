package com.cps.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.PatientRequest;
import com.cps.app.dto.response.PatientResponse;
import com.cps.app.mapper.PatientMapper;
import com.cps.app.model.Patient;
import com.cps.app.repo.PatientRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PatientService {

	private PatientRepository patientRepository;
	private LogEntryService logEntryService;

	public PatientService(PatientRepository patientRepository, LogEntryService logEntryService) {
		this.patientRepository = patientRepository;
		this.logEntryService = logEntryService;
	}
	
	public Patient getPatientById(Long id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));
	}
	public List<PatientResponse> getAllPatients(){
		logEntryService.info("toutes les Patients sont récupérées", "PatientService");
		return patientRepository
				.findAll()
				.stream()
				.map(PatientMapper::toPatientResponse)
				.toList();
	}

	
	public ResponseEntity<ApiResponse<PatientResponse>> createPatient(PatientRequest req) {
	    try {
	    	Patient p = new Patient(req);
	        p = patientRepository.save(p);
	        
	        PatientResponse PatientDto = PatientMapper.toPatientResponse(p);
	        SuccessResponse<PatientResponse> successResponse = new SuccessResponse<>("patient a été créée avec succès", PatientDto);
	        logEntryService.info("toutes les Patients sont récupérées", "PatientService");
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<PatientResponse> errorResponse = new ErrorResponse<>("Échec de la création de patient", null);
	        logEntryService.info("toutes les Patients sont récupérées", "PatientService");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
		
	
	public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(Patient body, Long id) {
		try {			
			Patient p = getPatientById(id);
			p.setNom(body.getNom());
			p.setPrenom(body.getPrenom());
			p.setDateNaissance(body.getDateNaissance());
			p.setCin(body.getCin());
			p.setTelephone(body.getTelephone());
			p.setEmail(body.getEmail());
			p.setAdresse(body.getAdresse());
			p.setSexe(body.getSexe());
			
			patientRepository.save(p);
			
			PatientResponse PatientDto = PatientMapper.toPatientResponse(p);
	        SuccessResponse<PatientResponse> successResponse = new SuccessResponse<>("patient a été mise à jour avec succès", PatientDto);
	        logEntryService.info("patient a été mise à jour avec succès avec id: "+id.toString(), "PatientService");
	        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<PatientResponse> errorResponse = new ErrorResponse<>("échec de la mise à jour de patient", null);
	        logEntryService.error("échec de la mise à jour de patient avec id: "+id.toString(), "PatientService");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public ResponseEntity<ApiResponse<PatientResponse>> deletePatient(Long id) {
		try {
		Patient a = getPatientById(id);
		PatientResponse PatientDto = PatientMapper.toPatientResponse(a);
		patientRepository.delete(a);
        SuccessResponse<PatientResponse> successResponse = new SuccessResponse<>("patient a été supprimée avec succès", PatientDto);
        logEntryService.info("patient a été supprimée avec succès avec id: "+id.toString(), "PatientService");
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
        ErrorResponse<PatientResponse> errorResponse = new ErrorResponse<>("échec de la suppression de patient", null);
        logEntryService.error("échec de la suppression de patient avec id: "+id.toString(),"PatientService");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
	
	
}
