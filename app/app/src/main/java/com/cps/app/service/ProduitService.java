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
	
	public ProduitService(ProduitRepository produitRepository) {
		super();
		this.produitRepository = produitRepository;
	}




	public Produit getProduitById(Long id) {
		return produitRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Rdv not found with id: " + id));
	}


	public List<Produit> getAllProduits(){
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
			
			produitRepository.save(p);

	    } catch (Exception e) {
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
			
	    } catch (Exception e) {
	    	throw new Exception("error with updating the product");
	    }
	}
	
	public void deleteProduit(Long id) throws Exception {
		try {
			
			Produit p = getProduitById(id);
			produitRepository.delete(p);
		
    } catch (Exception e) {
    	throw new Exception("error in deleting the product");
    }
	}
}
