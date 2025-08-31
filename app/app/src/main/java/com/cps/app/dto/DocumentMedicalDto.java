package com.cps.app.dto;

import java.time.LocalDateTime;

import com.cps.app.dto.response.PatientResponse;
import com.cps.app.enums.TypeDocumentMedical;

public record DocumentMedicalDto(
		Long id,
		TypeDocumentMedical TypeDocument,
		String nom,
		String filePath,
		String extension,
		LocalDateTime uploadDate,
		PatientResponse patient
		) {}
