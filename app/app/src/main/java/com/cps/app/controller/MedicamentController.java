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
import com.cps.app.dto.request.MedicamentRequest;
import com.cps.app.dto.response.MedicamentResponse;
import com.cps.app.mapper.MedicamentMapper;
import com.cps.app.model.Medicament;
import com.cps.app.service.MedicamentService;

@RestController
@RequestMapping("/medicaments")
public class MedicamentController {
	
	@Autowired
	private MedicamentService medicamentService;
	
	@GetMapping("")
	public List<MedicamentResponse> geAlltMedicaments(){
		return medicamentService.geAlltMedicaments();
	}
	
    @GetMapping("/{id}")
    public MedicamentResponse getActiviteById(@PathVariable Long id){
    	Medicament medicament = medicamentService.getMedicamentById(id);
    	return  MedicamentMapper.toMedicamentResponse(medicament);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<MedicamentResponse>> createMedicament(@RequestBody MedicamentRequest req){
    	try {    		
    		MedicamentResponse mr = medicamentService.createMedicament(req);
    		SuccessResponse<MedicamentResponse> successResponse = new SuccessResponse<>("Medicament a été créée avec succès", mr);
    		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    	}catch (Exception e) {
            ErrorResponse<MedicamentResponse> errorResponse = new ErrorResponse<>("Échec de la création de Medicament" + e, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicamentResponse>> updateMedicament(@RequestBody MedicamentRequest req, @PathVariable Long id){
    	try {    		
    		MedicamentResponse mr = medicamentService.updateMedicament(req, id);
    		SuccessResponse<MedicamentResponse> successResponse = new SuccessResponse<>("Medicament a été mise à jour avec succès", mr);
    		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    	}catch (Exception e) {
            ErrorResponse<MedicamentResponse> errorResponse = new ErrorResponse<>("échec de la mise à jour de Medicament", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMedicament(@PathVariable Long id){
    	try {    		
    		medicamentService.deleteMedicament(id);
    		SuccessResponse<Void> successResponse = new SuccessResponse<>("Medicament a été supprimée avec succès", null);
    		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    	}catch (Exception e) {
            ErrorResponse<Void> errorResponse = new ErrorResponse<>("échec de la suppression de Medicament", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
	

}
