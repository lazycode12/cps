package com.cps.app.enums;

public enum TypeDocumentMedical {
	ANALYSE("analyse"),
	RADIO("radio"),
	COMPTE_RENDU("compte rendu");
	
    private final String label;

	TypeDocumentMedical(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
