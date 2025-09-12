package com.cps.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cps.app.dto.request.OrdonanceRequest;
import com.cps.app.service.PdfGenerationService;

@RestController
@RequestMapping("/ordonance")
public class OrdonanceController {
	
	@Autowired
	private PdfGenerationService pdfGenerationService;
	
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateOrdonance(@RequestBody OrdonanceRequest request) {
        byte[] pdfBytes = pdfGenerationService.generateOrdonance(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.builder("inline") // 👈 open in browser
                        .filename("ordonnance.pdf")
                        .build()
                );

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }


}
