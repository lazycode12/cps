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
import com.cps.app.dto.request.RdvRequest;
import com.cps.app.dto.response.RdvResponse;
import com.cps.app.mapper.RdvMapper;
import com.cps.app.model.Rdv;
import com.cps.app.service.RdvService;

@RestController
@RequestMapping("/rdvs")
@PreAuthorize("hasAuthority('gestion-rdvs')")
public class RdvController {
	@Autowired
	private RdvService rdvService;
	
	@GetMapping("")
	public List<RdvResponse> getAllRdvs(){
		return rdvService.getAllRdv();
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<RdvResponse> getUserById(@PathVariable Long id){
    	Rdv rdv = rdvService.getRdvById(id);
    	return new ResponseEntity<>(RdvMapper.toRdvResponse(rdv), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<ApiResponse<RdvResponse>> createUser(@RequestBody RdvRequest rdv){
    	return rdvService.createRdv(rdv);
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RdvResponse>> updateUser(@RequestBody Rdv rdv, @PathVariable Long id){
    	return rdvService.updateRdv(rdv, id);
    	
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<RdvResponse>> deleteUser(@PathVariable Long id){
    	return rdvService.deleteRdv(id);
    }
}
