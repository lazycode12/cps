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
import com.cps.app.dto.request.PatientRequest;
import com.cps.app.dto.response.PatientResponse;
import com.cps.app.mapper.PatientMapper;
import com.cps.app.model.Patient;
import com.cps.app.service.PatientService;

@RestController
@RequestMapping("/patients")
@PreAuthorize("hasAuthority('gestion-patients')")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@GetMapping("")
	public List<PatientResponse> getAllusers(){
		return patientService.getAllPatients();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getUserById(@PathVariable Long id){
    	Patient patient = patientService.getPatientById(id);
    	return new ResponseEntity<>(PatientMapper.toPatientResponse(patient), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<PatientResponse>> createUser(@RequestBody PatientRequest patient){
    	return patientService.createPatient(patient);
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> updateUser(@RequestBody Patient patient, @PathVariable Long id){
    	return patientService.updatePatient(patient, id);
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> deleteUser(@PathVariable Long id){
    	return patientService.deletePatient(id);
    }
}
