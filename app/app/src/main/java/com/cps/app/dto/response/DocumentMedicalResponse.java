package com.cps.app.dto.response;

import java.time.LocalDateTime;


public record DocumentMedicalResponse(
		Long id,
		String typeDocument,
		String nom,
		String filePath,
		String extension,
		LocalDateTime uploadDate
		) {}
