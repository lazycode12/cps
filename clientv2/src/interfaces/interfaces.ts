export interface Permission{
    id: number,
    nom: string,
    description: string
}

export interface Role{
    id: number,
    nom: string,
    description: string,
    permissions: Permission[]
}

export interface Activite{
    id: number,
	type: string,
	statut: string,
	date: string
}

export interface Facture{
    id: number,
    montant: number,
    statut: string,
    datePaiment: string,
    consultation: ConsultationSummary

}

export interface Consultation{
    id: number,
    dateDebut: string,
    dateFin: string,
    patient: Patient,
    rdv: RdvSummary | null,
    facture: Facture | null
}

export interface ConsultationForActivity{
    id: number,
    dateDebut: string,
    dateFin: string,
    patient: string
}

export interface Patient{
    id: number,
    nom: string,
    prenom: string,
    dateNaissance: string,
    cin: string,
    telephone: string,
    email: string,
    adresse: string,
    sexe: string
}

export interface ConsultationSummary{
    id: number,
    dateDebut: string,
    dateFin: string
}

export interface Rdv{
    id: number,
    motif: string,
    statut: string,
    date: string,
    consultation: ConsultationSummary,
    patient: string
}

export interface RdvSummary{
    id: number,
    motif: string,
    statut: string,
    date: string,
}

export interface Medicament{
    id: number,
    nom: string,
    qte: number,
    description: string

}