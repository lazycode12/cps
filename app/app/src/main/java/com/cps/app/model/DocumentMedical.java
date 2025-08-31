package com.cps.app.model;

import java.time.LocalDateTime;

import com.cps.app.enums.TypeDocumentMedical;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "document_medical")
public class DocumentMedical {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "type_document", nullable = false)
    private TypeDocumentMedical typeDocument;
    
    @Column(name = "nom_document", nullable = false)
    private String nom;
    
    @Column(name = "file_path", nullable = false)
    private String filePath;
    
    @Column(name = "extension", nullable = false)
    private String extension;
    
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

	public DocumentMedical() {
		super();
	}

	public DocumentMedical(TypeDocumentMedical typeDocument, String nom, String filePath, LocalDateTime uploadDate, String extension,
			Patient patient) {
		super();
		this.typeDocument = typeDocument;
		this.nom = nom;
		this.filePath = filePath;
		this.uploadDate = uploadDate;
		this.extension = extension;
		this.patient = patient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public TypeDocumentMedical getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocumentMedical typeDocument) {
		this.typeDocument = typeDocument;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
    
    
    

}
