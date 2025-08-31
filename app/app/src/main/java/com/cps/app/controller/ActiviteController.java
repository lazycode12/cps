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
import com.cps.app.dto.request.ActiviteRequest;
import com.cps.app.dto.response.ActiviteResponse;
import com.cps.app.mapper.ActivityMapper;
import com.cps.app.model.Activite;
import com.cps.app.service.ActiviteService;

@RestController
@RequestMapping("/activites")
public class ActiviteController {
	
	@Autowired
	private ActiviteService activiteService;
	
	@GetMapping("")
	public List<ActiviteResponse> getAllActivites(){
		return activiteService.getAllActivites();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<ActiviteResponse> getActiviteById(@PathVariable Long id){
    	Activite activite = activiteService.getActiviteById(id);
    	return new ResponseEntity<>(ActivityMapper.toActiviteResponse(activite), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<ActiviteResponse>> createActivite(@RequestBody ActiviteRequest activite){
    	return activiteService.createActivite(activite);
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ActiviteResponse>> updateActivite(@RequestBody Activite activite, @PathVariable Long id){
    	return activiteService.updateActivite(activite, id);
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ActiviteResponse>> deleteActivite(@PathVariable Long id){
    	return activiteService.deleteActivite(id);
    }
}
