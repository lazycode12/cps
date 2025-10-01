package com.cps.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.DocumentMedicalRequest;
import com.cps.app.dto.response.DocumentMedicalResponse;
import com.cps.app.mapper.DocumentMedicalMapper;
import com.cps.app.model.DocumentMedical;
import com.cps.app.model.Patient;
import com.cps.app.repo.DocumentMedicalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DocumentMedicalService {

	
	private DocumentMedicalRepository documentMedicalRepository;
	private PatientService patientService;
	private LogEntryService logEntryService;
	
	private final Path rootLocation = Paths.get("uploads");
	
	public DocumentMedicalService(DocumentMedicalRepository documentMedicalRepository, PatientService patientService, LogEntryService logEntryService) {
		this.documentMedicalRepository = documentMedicalRepository;
		this.patientService = patientService;
		this.logEntryService = logEntryService;
	}


	@Transactional
	public ResponseEntity<ApiResponse<DocumentMedicalResponse>> uploadMedicalDocument(MultipartFile file , DocumentMedicalRequest req) throws IOException {
		
		Patient patient = patientService.getPatientById(req.patientId());
		
		
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = String.format(
        	    "%s_%s_%s%s",
        	    patient.getNom(),
        	    patient.getPrenom(),
        	    UUID.randomUUID().toString().substring(0, 8),
        	    fileExtension
        	);
        
        // Create upload directory if it doesn't exist
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
        
        // Save file to filesystem
        Path destinationFile = rootLocation.resolve(Paths.get(uniqueFileName)).normalize().toAbsolutePath();
        file.transferTo(destinationFile.toFile());
        
        try {
        	// Create and save medical file entity
        	DocumentMedical medicalFile = new DocumentMedical();
        	medicalFile.setTypeDocument(req.fileType());
        	medicalFile.setNom(uniqueFileName);
        	medicalFile.setUploadDate(LocalDateTime.now());
        	medicalFile.setFilePath(destinationFile.toString());
        	medicalFile.setExtension(fileExtension);
        	medicalFile.setPatient(patient);
        	
        	DocumentMedical d = documentMedicalRepository.save(medicalFile);
        	
        	DocumentMedicalResponse documentMedicalDto = DocumentMedicalMapper.toDto(d);
	        SuccessResponse<DocumentMedicalResponse> successResponse = new SuccessResponse<>("le document a été téléchargé avec succès" , documentMedicalDto);
	        logEntryService.info("document a été téléchargé avec succès pour le patient avec id: " + req.patientId().toString(), "DocumentMedicalService");
	        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        	
        }catch (Exception e) {
	        ErrorResponse<DocumentMedicalResponse> errorResponse = new ErrorResponse<>("échec du téléchargement du document " + e, null);
	        logEntryService.error("échec du téléchargement du document medical pour le patient avec id: "+req.patientId().toString(), "DocumentMedicalService");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
        
	}
	
	public List<DocumentMedicalResponse> findByPatientId(Long id){
		logEntryService.info("toutes les documents medicales pour le patient  avec id "+ id.toString() + "sont récupérées", "DocumentMedicalService");
		return documentMedicalRepository
				.findByPatientId(id)
				.stream()
				.map(DocumentMedicalMapper::toDto)
				.toList();
	}

	public List<DocumentMedicalResponse> getAllDocuments(){
		logEntryService.info("toutes les documents medicales sont récupérées", "DocumentMedicalService");
		return documentMedicalRepository
				.findAll()
				.stream()
				.map(DocumentMedicalMapper::toDto)
				.toList();
	}
	
	public DocumentMedical getDocumentMedicalById(Long id) {
		logEntryService.info("Document Medical récupérée avec id: "+id, "DocumentMedicalService");
		return documentMedicalRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("DocumentMedical not found with id: " + id));
	}
	
	public ResponseEntity<ApiResponse<DocumentMedicalResponse>> deleteDocumentMedical(Long id) {
		try {
		DocumentMedical d = getDocumentMedicalById(id);
		DocumentMedicalResponse activityDto = DocumentMedicalMapper.toDto(d);
		documentMedicalRepository.delete(d);
        SuccessResponse<DocumentMedicalResponse> successResponse = new SuccessResponse<>("Document medical a été supprimée avec succès id: "+id.toString(), activityDto);
        logEntryService.info("toutes les Consultations sont récupérées", "DocumentMedicalService");
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    } catch (Exception e) {
        ErrorResponse<DocumentMedicalResponse> errorResponse = new ErrorResponse<>("échec de la suppression de Document medical id: "+id.toString(), null);
        logEntryService.error("échec de la suppression de Document medical id : "+id.toString(), "DocumentMedicalService");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	}
	
}
