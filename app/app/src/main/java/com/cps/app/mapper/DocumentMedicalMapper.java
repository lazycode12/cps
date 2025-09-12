package com.cps.app.mapper;

import com.cps.app.dto.response.DocumentMedicalResponse;
import com.cps.app.model.DocumentMedical;

public class DocumentMedicalMapper {
	public static DocumentMedicalResponse toDto(DocumentMedical documentMedical) {
		return new DocumentMedicalResponse(
				documentMedical.getId(),
				documentMedical.getTypeDocument().getLabel(),
				documentMedical.getNom(),
				documentMedical.getFilePath(),
				documentMedical.getExtension(),
				documentMedical.getUploadDate());
	}
}
