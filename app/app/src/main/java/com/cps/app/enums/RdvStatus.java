package com.cps.app.enums;

public enum RdvStatus {
    CONFIRME("Confirmé"),
    REPORTE("Reporté"),
    ANNULE("Annulé"),
    TERMINE("Terminé");
    
    private final String description;
    
    RdvStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
