package com.cps.app.enums;

public enum StatutActivite {
    PLANIFIEE("Planifiée"),
    EN_COURS("En cours"),
    TERMINEE("Terminée"),
    ANNULEE("Annulée"),
    EN_ATTENTE("En attente"),
    REPORTEE("Reportée");

    private final String label;

    StatutActivite(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

