package com.cps.app.enums;

public enum TypeActivite {
    ANALYSE("Analyse"),
    RADIO("Radio"),
    COMPTE_RENDU("Compte rendu"),
    TRAITEMENT("Traitement"),
    SUIVI("Suivi"),
    VACCINATION("Vaccination"),
    URGENCE("Urgence");

    private final String label;

    TypeActivite(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

