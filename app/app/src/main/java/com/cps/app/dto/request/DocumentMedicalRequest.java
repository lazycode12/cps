package com.cps.app.dto.request;

import com.cps.app.enums.TypeDocumentMedical;

public record DocumentMedicalRequest(
		TypeDocumentMedical fileType,
		Long patientId
		) {}
