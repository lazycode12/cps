package com.cps.app.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cps.app.dto.request.OrdonanceRequest;
import com.cps.app.model.Consultation;
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
	private MedicamentRepository medicamentRepository;
	
	
	
	 public PdfGenerationService(ConsultationService consultationService, MedicamentRepository medicamentRepository) {
		super();
		this.consultationService = consultationService;
		this.medicamentRepository = medicamentRepository;
	}

	public byte[] generateOrdonance(OrdonanceRequest req) {
		 
		 Consultation c = consultationService.getConsultationById(req.consultationId());
		 List<Medicament> meds = medicamentRepository.findAllById(req.medicamentsIds());
		 
		 
		 
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
	    
	    // info
	    document.add(new Paragraph(req.description()));
	    
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

	    // Médicaments (table)
	    Table table = new Table(new float[]{1, 3, 2});
	    table.setWidth(UnitValue.createPercentValue(100));

	    table.addHeaderCell(new Cell().add(new Paragraph("N°").setBold()));
	    table.addHeaderCell(new Cell().add(new Paragraph("Médicament").setBold()));

	    int index = 1;
	    for (Medicament med : meds) {
	        table.addCell(String.valueOf(index++));
	        table.addCell(med.getNom());
	    }
	    document.add(table);

	    // Footer
	    document.add(new Paragraph("\n\nDate : " + LocalDate.now()));
	    document.add(new Paragraph("Signature du médecin : _____________________")
	            .setTextAlignment(TextAlignment.RIGHT));
		 
        
        document.close();
        return out.toByteArray();
		 
	 }
	

}
