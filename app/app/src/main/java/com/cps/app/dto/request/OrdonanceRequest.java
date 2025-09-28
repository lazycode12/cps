package com.cps.app.dto.request;

import java.util.List;

public record OrdonanceRequest(
        Long consultationId,
        List<MedReq> req
) {
    public static record MedReq(
            String nom,
            String dosage,
            String forme,
            String posologie,
            String duree
    ) {}
}

