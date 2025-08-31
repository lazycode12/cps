package com.cps.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.FactureRequest;
import com.cps.app.dto.response.FactureResponse;
import com.cps.app.enums.FactureStatut;
import com.cps.app.mapper.FactureMapper;
import com.cps.app.model.Consultation;
import com.cps.app.model.Facture;
import com.cps.app.repo.FactureRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FactureService {

	private FactureRepository factureRepository;
	private ConsultationService consultationService;
	
	
	
	public FactureService(FactureRepository factureRepository, ConsultationService consultationService) {
		super();
		this.factureRepository = factureRepository;
		this.consultationService = consultationService;
	}


	public Facture getFactureById(Long id) {
		return factureRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Activite not found with id: " + id));
	}


	public List<FactureResponse> getAllFactures(){
		return factureRepository
				.findAll()
				.stream()
				.map(FactureMapper::toDto)
				.toList();
	}

	
	// It's crucial that this method is @Transactional
	public ResponseEntity<ApiResponse<FactureResponse>> createFacture(FactureRequest req) {
	    Facture f = new Facture();
	    
	    // 1. First, fetch the consultation OUTSIDE of the try block or handle its exception specifically
	    Consultation c;
	    try {
	        // This method likely throws a RuntimeException (e.g., NoSuchElementException) if not found
	        c = consultationService.getConsultationById(req.consultationId());
	    } catch (RuntimeException e) {
	        // Handle the "not found" case explicitly, without marking the transaction for rollback
	        ErrorResponse<FactureResponse> errorResponse = new ErrorResponse<>("Consultation introuvable pour l'ID : " + req.consultationId(), null);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	    }

	    // 2. Check for duplicate invoice
	    if (c.getFacture() != null) {
	        ErrorResponse<FactureResponse> errorResponse = new ErrorResponse<>("Cette consultation a déjà une facture.", null);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }

	    // 3. Now proceed with the creation logic in a try-catch for other unexpected errors
	    try {        
	        f.setMontant(req.montant());        
	        f.setStatut(req.statut());
	        if(req.statut().equals(FactureStatut.PAYEE)) {        	
	        	f.setDatePaiment(req.datePaiment());
	        }
	        
	        // Link the entities bidirectionally
	        f.setConsultation(c);
	        c.setFacture(f); // This is important for the ORM mapping
	        
	        // Save the Facture. Due to the bidirectional link and transactional context,
	        // the state of the Consultation object is also managed.
	        f = factureRepository.save(f);
	        // You do NOT need to explicitly save the Consultation again.
	        
	        FactureResponse factureDto = FactureMapper.toDto(f);
	        SuccessResponse<FactureResponse> successResponse = new SuccessResponse<>("Facture a été créée avec succès", factureDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	        
	    } catch (Exception e) {
	        // This catches any other unexpected error during the creation/persistence process.
	        // Since this is inside a @Transactional method, this exception will automatically
	        // mark the transaction for rollback and then re-throw it.
	        // We catch it here to log it and return a nice message, but we must re-throw it!
	        throw new RuntimeException("Échec de la création de facture: " + e.getMessage(), e);
	        
	        // Alternatively, if you don't want to re-throw, you can explicitly roll back:
	        // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	        // ErrorResponse<FactureResponse> errorResponse = new ErrorResponse<>("Échec de la création de facture: " + e.getMessage(), null);
	        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
		
	
	public ResponseEntity<ApiResponse<FactureResponse>> updateFacture(Facture body, Long id) {
		try {			
			Facture f = getFactureById(id);
			f.setMontant(body.getMontant());
			f.setDatePaiment(body.getDatePaiment());
			f.setStatut(body.getStatut());
			
			factureRepository.save(f);
			FactureResponse factureDto = FactureMapper.toDto(f);
	        SuccessResponse<FactureResponse> successResponse = new SuccessResponse<>("le facture a été mise à jour avec succès", factureDto);
	        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<FactureResponse> errorResponse = new ErrorResponse<>("échec de la mise à jour de facture", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public ResponseEntity<ApiResponse<FactureResponse>> deleteFacture(Long id) {
		try {
		Facture f = getFactureById(id);
		FactureResponse factureDto = FactureMapper.toDto(f);
		factureRepository.delete(f);
        SuccessResponse<FactureResponse> successResponse = new SuccessResponse<>("le facture a été supprimée avec succès", factureDto);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
        ErrorResponse<FactureResponse> errorResponse = new ErrorResponse<>("échec de la suppression de facture", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
	
}
