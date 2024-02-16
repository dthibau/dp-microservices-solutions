package org.formation.web;

import java.util.List;
import java.util.Optional;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.repository.LivraisonRepository;
import org.formation.domain.repository.LivreurRepository;
import org.formation.service.LivraisonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/livraisons")
public class LivraisonController {

	private final LivraisonRepository livraisonRepository;
	private final LivreurRepository livreurRepository;
	private final LivraisonService livraisonService;

	public LivraisonController(LivraisonRepository livraisonRepository, LivreurRepository livreurRepository, LivraisonService livraisonService) {
		this.livraisonRepository = livraisonRepository;
		this.livreurRepository = livreurRepository;
		this.livraisonService = livraisonService;
	}

	@GetMapping
	List<Livraison> findAll(@RequestParam Optional<Long> orderId) {
		return livraisonRepository.findAll();
	}

	@GetMapping(path = "/unaffected")
	public List<Livraison> getUnaffectedLivraison() {
		return livraisonRepository.findUnaffected();
	}
	
	@GetMapping(path = "/affected")
	public List<Livraison> getAffectedLivraison() {
		return livraisonRepository.findAffected();
	}

	@GetMapping("/orders/{id}")
	Livraison findByOrderId(@PathVariable Long orderId) {
		return livraisonRepository.findByOrderId(orderId).orElseThrow();
	}

	@GetMapping(path = "/{livraisonId}")
	public Livraison getLivraison(@PathVariable Long livraisonId) {
		return livraisonRepository.findById(livraisonId).orElseThrow();
	}

	@PostMapping(path = "/{livraisonId}/affect/{livreurId}")
	public Livraison affectCourier(@PathVariable long livraisonId, @PathVariable long livreurId) {

		
		Livraison livraison = livraisonRepository.findById(livraisonId).orElseThrow();
		Livreur livreur = livreurRepository.findById(livreurId).orElseThrow();
		return livraisonService.affect(livraison,livreur);
	
	}

	@PostMapping(path = "/pick/{deliveryId}")
	public ResponseEntity<Void> noteDeliveryPickedUp(@PathVariable long deliveryId) {
		return null;
	}

	@PostMapping(path = "/delivered/{deliveryId}")
	public ResponseEntity<Void> noteDeliveryDelivered(@PathVariable long deliveryId) {
		return null;
	}

}
