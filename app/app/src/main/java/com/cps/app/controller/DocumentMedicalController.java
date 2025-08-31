package com.cps.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.request.DocumentMedicalRequest;
import com.cps.app.dto.response.DocumentMedicalResponse;
import com.cps.app.enums.TypeDocumentMedical;
import com.cps.app.mapper.DocumentMedicalMapper;
import com.cps.app.model.DocumentMedical;
import com.cps.app.service.DocumentMedicalService;

@RestController
@RequestMapping("/documents-medicales")
public class DocumentMedicalController {
	@Autowired
	private DocumentMedicalService documentMedicalService;
	
	@GetMapping("")
	public List<DocumentMedicalResponse> getAllDocumentMedicals(){
		return documentMedicalService.getAllDocuments();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<DocumentMedicalResponse> getDocumentMedicalById(@PathVariable Long id){
    	DocumentMedical role = documentMedicalService.getDocumentMedicalById(id);
    	return new ResponseEntity<>(DocumentMedicalMapper.toDto(role), HttpStatus.OK);
    }
    
    @GetMapping("bypatient/{id}")
    public List<DocumentMedicalResponse>findByPatientId(@PathVariable Long id){
    	return documentMedicalService.findByPatientId(id);
    }
    
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DocumentMedicalResponse>> createDocumentMedical(
    		@RequestPart("file") MultipartFile file, 
    		@RequestPart("req") DocumentMedicalRequest req) throws IOException{
    	return documentMedicalService.uploadMedicalDocument(file, req);	
    }
    
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<DocumentMedicalResponse>> updateDocumentMedical(DocumentMedical role, @PathVariable Long id){
//    	return documentMedicalService.updateDocumentMedical(role, id);
//    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DocumentMedicalResponse>> deleteDocumentMedical(@PathVariable Long id){
    	return documentMedicalService.deleteDocumentMedical(id);
    }
}
