package com.cps.app.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cps.app.dto.ApiResponse;
import com.cps.app.dto.ErrorResponse;
import com.cps.app.dto.SuccessResponse;
import com.cps.app.dto.request.RdvRequest;
import com.cps.app.dto.response.RdvResponse;
import com.cps.app.mapper.RdvMapper;
import com.cps.app.model.Consultation;
import com.cps.app.model.Produit;
import com.cps.app.model.Rdv;
import com.cps.app.repo.ConsultationRepository;
import com.cps.app.repo.ProduitRepository;
import com.cps.app.repo.RdvRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProduitService {
	private ProduitRepository produitRepository;
	private LogEntryService logEntryService;
	
	public ProduitService(ProduitRepository produitRepository, LogEntryService logEntryService) {
		super();
		this.produitRepository = produitRepository;
		this.logEntryService = logEntryService;
	}




	public Produit getProduitById(Long id) {
		return produitRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Rdv not found with id: " + id));
	}


	public List<Produit> getAllProduits(){
		logEntryService.info("toutes les Produits sont récupérées", "ProduitService");
		return produitRepository.findAll();
	}

//	@Transactional
	public void createProduit(Produit req) throws Exception {
	    try {
	    	Produit p = new Produit();
	    	
			p.setCategorie(req.getCategorie());
			p.setNom(req.getNom());
			p.setUnite(req.getUnite());
			p.setQuantite(req.getQuantite());
			p.setSeuilReapprovisionnement(req.getSeuilReapprovisionnement());
			
			logEntryService.info("permission a été créée avec succès", "ProduitService");
			
			produitRepository.save(p);

	    } catch (Exception e) {
	    	logEntryService.error("Échec de la création de Produit", "ProduitService");
	    	throw new Exception("error in creatiang the product" + e.getMessage());
	    }
	}
		
	
	public void updateProduit(Produit body, Long id) throws Exception {
		try {			
			Produit p = getProduitById(id);
			
			p.setCategorie(body.getCategorie());
			p.setNom(body.getNom());
			p.setUnite(body.getUnite());
			p.setQuantite(body.getQuantite());
			p.setSeuilReapprovisionnement(body.getSeuilReapprovisionnement());
			
			produitRepository.save(p);
			logEntryService.info("produit a été mise à jour avec succès avec id: "+id.toString(), "ProduitService");
	    } catch (Exception e) {
	    	logEntryService.error("échec de la mise à jour produit avec id: "+id.toString(), "ProduitService");
	    	throw new Exception("error with updating the product");
	    }
	}
	
	public void deleteProduit(Long id) throws Exception {
		try {
			
			Produit p = getProduitById(id);
			produitRepository.delete(p);
			logEntryService.info("produit a été supprimée avec succès avec id: "+id.toString(), "ProduitService");
		
    } catch (Exception e) {
    	logEntryService.error("échec de la suppression de produit avec id: "+id.toString(), "ProduitService");
    	throw new Exception("error in deleting the product");
    }
	}
}
