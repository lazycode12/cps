package com.cps.app.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.cps.app.enums.TypeDocumentMedical;

public record DocumentMedicalRequest(
		TypeDocumentMedical fileType,
		Long patientId
		) {}
