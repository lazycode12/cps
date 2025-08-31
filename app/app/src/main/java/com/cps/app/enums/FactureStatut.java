package com.cps.app.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum FactureStatut {
    NON_PAYEE("Non payée"),
    PAYEE("Payée"),
    PARTIELLEMENT_PAYEE("Partiellement payée"),
    ANNULEE("Annulée"),
    EN_ATTENTE("En attente de paiement");

    private final String label;

    FactureStatut(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // Pour exposer {value, label} facilement
    public static List<Map<String, String>> asList() {
        return Arrays.stream(values())
                .map(v -> Map.of("value", v.name(), "label", v.getLabel()))
                .toList();
    }
}

