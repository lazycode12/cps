package com.cps.app.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cps.app.dto.request.OrdonanceRequest;
import com.cps.app.model.Consultation;
import com.cps.app.model.Facture;
import com.cps.app.model.Medicament;
import com.cps.app.model.Patient;
import com.cps.app.repo.MedicamentRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Service
public class PdfGenerationService {
	
	private ConsultationService consultationService;
	private LogEntryService logEntryService;
	
	 public PdfGenerationService(ConsultationService consultationService, MedicamentRepository medicamentRepository, LogEntryService logEntryService) {
		super();
		this.consultationService = consultationService;
		this.logEntryService = logEntryService;
	}

	 public byte[] generateOrdonance(OrdonanceRequest req) {
		    
		    Consultation c = consultationService.getConsultationById(req.consultationId());
		    
		    // Create a ByteArrayOutputStream to hold the PDF data
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    
		    // Initialize PDF writer and document
		    PdfWriter writer = new PdfWriter(out);
		    PdfDocument pdfDoc = new PdfDocument(writer);
		    Document document = new Document(pdfDoc);
		    
		    // Title
		    Paragraph title = new Paragraph("Ordonnance Médicale")
		            .setFontSize(18)
		            .setBold()
		            .setTextAlignment(TextAlignment.CENTER)
		            .setMarginBottom(20);
		    document.add(title);
		    
		    // Patient information
		    Patient p = c.getPatient();
		    document.add(new Paragraph("Patient : " + p.getNom() + " " + p.getPrenom()));
		    document.add(new Paragraph("CIN : " + p.getCin()));
		    document.add(new Paragraph("Date de naissance : " + p.getDateNaissance()));
		    document.add(new Paragraph("Téléphone : " + p.getTelephone()));
		    document.add(new Paragraph(" "));

		    // Consultation info
		    document.add(new Paragraph("Consultation du : " + c.getDateDebut()));
		    document.add(new Paragraph(" "));

		    // Médicaments (table) - Updated to use MedReq data
		    Table table = new Table(new float[]{1, 3, 2, 2, 3, 2});
		    table.setWidth(UnitValue.createPercentValue(100));

		    table.addHeaderCell(new Cell().add(new Paragraph("N°").setBold()));
		    table.addHeaderCell(new Cell().add(new Paragraph("Médicament").setBold()));
		    table.addHeaderCell(new Cell().add(new Paragraph("Dosage").setBold()));
		    table.addHeaderCell(new Cell().add(new Paragraph("Forme").setBold()));
		    table.addHeaderCell(new Cell().add(new Paragraph("Posologie").setBold()));
		    table.addHeaderCell(new Cell().add(new Paragraph("Durée").setBold()));

		    int index = 1;
		    for (OrdonanceRequest.MedReq med : req.req()) {
		        table.addCell(String.valueOf(index++));
		        table.addCell(med.nom());
		        table.addCell(med.dosage());
		        table.addCell(med.forme());
		        table.addCell(med.posologie());
		        table.addCell(med.duree());
		    }
		    document.add(table);

		    // Footer
		    document.add(new Paragraph("\n\nDate : " + LocalDate.now()));
		    document.add(new Paragraph("Signature du médecin : _____________________")
		            .setTextAlignment(TextAlignment.RIGHT));
		    
		    document.close();
		    logEntryService.info("ordonance a été generer avec succès", "PdfGenerationService");
		    return out.toByteArray();
		}

	
	
	 public byte[] generateFacture(long consultationId) {
	        Consultation consultation = consultationService.getConsultationById(consultationId);

	        Facture facture = consultation.getFacture();
	        if (facture == null) {
	            throw new RuntimeException("No facture linked to this consultation");
	        }

	        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
	            PdfWriter writer = new PdfWriter(baos);
	            PdfDocument pdf = new PdfDocument(writer);
	            Document document = new Document(pdf);

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	            // --- HEADER ---
	            document.add(new Paragraph("Cabinet Médical")
	                    .setBold()
	                    .setFontSize(18));
	            document.add(new Paragraph("Facture N°: " + facture.getId()).setFontSize(12));
	            document.add(new Paragraph("Date de paiement: " +
	                    (facture.getDatePaiment() != null ? facture.getDatePaiment().format(formatter) : "Non payé")));

	            document.add(new Paragraph("\n----------------------------\n"));

	            // --- CONSULTATION INFO ---
	            document.add(new Paragraph("Consultation du: " + consultation.getDateDebut().format(formatter)
	                    + " au " + consultation.getDateFin().format(formatter)));
	            document.add(new Paragraph("Type: " + consultation.getType().getLibelle()));
	            document.add(new Paragraph("Patient: " + consultation.getPatient().getNom() + " " +
	                    consultation.getPatient().getPrenom()));

	            document.add(new Paragraph("\n----------------------------\n"));

	            // --- MONTANT ---
	            document.add(new Paragraph("Montant total: " + facture.getMontant() + " MAD")
	                    .setBold()
	                    .setFontSize(14));

	            document.close();
	            logEntryService.info("facture a été generer avec succès", "PdfGenerationService");
	            return baos.toByteArray();

	        } catch (Exception e) {
	        	logEntryService.error("echec dans la genration de facture", "PdfGenerationService");
	            throw new RuntimeException("Erreur lors de la génération de la facture PDF", e);
	        }
	    }

}
