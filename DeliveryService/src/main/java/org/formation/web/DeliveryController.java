package org.formation.web;

import java.util.List;

import org.formation.domain.Livraison;
import org.formation.domain.LivraisonRepository;
import org.formation.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/livraison")
public class DeliveryController {

	@Autowired
	LivraisonRepository livraisonRepository;

	@Autowired
	DeliveryService deliveryService;

	@PostMapping(path = "/pick/{livraisonId}")
	public ResponseEntity<Void> noteDeliveryPickedUp(@PathVariable long livraisonId) {
		return null;
	}

	@PostMapping(path = "/position")
	public ResponseEntity<Void> updatePosition(@RequestBody Position position) {
		return null;
	}

	@PostMapping(path = "/delivered/{livraisonId}")
	public ResponseEntity<Void> noteDeliveryDelivered(@PathVariable long livraisonId) {
		return null;
	}

	@PostMapping(path = "/{livraisonId}/{livreurId}")
	public ResponseEntity<Livraison> affectLivreur(@PathVariable long livraisonId, @PathVariable long livreurId) {

		Livraison livraison = deliveryService.affectLivreur(livraisonId, livreurId);

		return new ResponseEntity<Livraison>(livraison, HttpStatus.OK);
	}

	@GetMapping(path = "/unaffected")
	public List<Livraison> getUnaffectedLivraison() {
		return livraisonRepository.findUnaffected();
	}

	@GetMapping(path = "/order/{orderId}")
	public Livraison getLivraisonByOrderId(Long orderId) {
		return livraisonRepository.findByOrderId(orderId);
	}

	@GetMapping(path = "/{livraisonId}")
	public Livraison getLivraison(@PathVariable Long livraisonId) {
		return livraisonRepository.findById(livraisonId).orElseThrow();
	}
}
