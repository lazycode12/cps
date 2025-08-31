package com.cps.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.response.RdvResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.RdvRequest;
import com.cps.app.mapper.RdvMapper;
import com.cps.app.model.Consultation;
import com.cps.app.model.Rdv;
import com.cps.app.repo.RdvRepository;
import com.cps.app.repo.ConsultationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RdvService {
	
	private RdvRepository rdvRepository;
	private ConsultationRepository consultationRepository;
	private ConsultationService consultationService;
	
	public RdvService(RdvRepository rdvRepository, ConsultationRepository consultationRepository,
			ConsultationService consultationService) {
		super();
		this.rdvRepository = rdvRepository;
		this.consultationRepository = consultationRepository;
		this.consultationService = consultationService;
	}




	public Rdv getRdvById(Long id) {
		return rdvRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Rdv not found with id: " + id));
	}


	public List<RdvResponse> getAllRdv(){
		return rdvRepository
				.findAll()
				.stream()
				.map(RdvMapper::toRdvResponse)
				.toList();
	}

//	@Transactional
	public ResponseEntity<ApiResponse<RdvResponse>> createRdv(RdvRequest req) {
	    try {
	    	Rdv r = new Rdv();
	    	Consultation c = consultationService.getConsultationById(req.consultationId());
	    	
			r.setDate(req.date());
			r.setMotif(req.motif());
			r.setStatut(req.statut());
			r.setConsultation(c);
			
			Rdv rdv = rdvRepository.save(r);
			
			c.setRdv(r);
			
			consultationRepository.save(c);
			
	        RdvResponse rdvDto = RdvMapper.toRdvResponse(rdv);
	        SuccessResponse<RdvResponse> successResponse = new SuccessResponse<>("Rdv  a été créée avec succès", rdvDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<RdvResponse> errorResponse = new ErrorResponse<>("Échec de la création de Rdv " + e , null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
		
	
	public ResponseEntity<ApiResponse<RdvResponse>> updateRdv(Rdv body, Long id) {
		try {			
			Rdv r = getRdvById(id);
			r.setDate(body.getDate());
			r.setMotif(body.getMotif());
			r.setStatut(body.getStatut());
			
			rdvRepository.save(r);
			RdvResponse RdvResponse = RdvMapper.toRdvResponse(r);
	        SuccessResponse<RdvResponse> successResponse = new SuccessResponse<>("Rdv a été mise à jour avec succès", RdvResponse);
	        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	    } catch (Exception e) {
	        ErrorResponse<RdvResponse> errorResponse = new ErrorResponse<>("échec de la mise à jour de Rdv", null);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	public ResponseEntity<ApiResponse<RdvResponse>> deleteRdv(Long id) {
		try {
			
		Rdv r = getRdvById(id);
		
		RdvResponse RdvResponse = RdvMapper.toRdvResponse(r);
		
		// Remove reference from consultation
        if (r.getConsultation() != null) {
            r.getConsultation().setRdv(null); 
            consultationRepository.save(r.getConsultation());
        }
		
		
		rdvRepository.delete(r);
		
        SuccessResponse<RdvResponse> successResponse = new SuccessResponse<>("Rdv a été supprimée avec succès", RdvResponse);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
        ErrorResponse<RdvResponse> errorResponse = new ErrorResponse<>("échec de la suppression de Rdv", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
}
