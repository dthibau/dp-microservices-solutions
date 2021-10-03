package org.formation.web;

import java.util.List;

import org.formation.domain.Courier;
import org.formation.domain.CourierRepository;
import org.formation.domain.Delivery;
import org.formation.domain.DeliveryRepository;
import org.formation.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

	@Autowired
	DeliveryRepository deliveryRepository;
	
	@Autowired
	CourierRepository courierRepository;
	
	@Autowired
	DeliveryService deliveryService;

	@GetMapping
	public List<Delivery> findDeliveries(@RequestParam(required = false) String status) {
		return deliveryRepository.findAll();
	}


	@PostMapping(path = "/{livraisonId}/{livreurId}")
	public ResponseEntity<Delivery> affectLivreur(@PathVariable long livraisonId, @PathVariable long livreurId) {

		Delivery livraison = deliveryService.affectLivreur(livraisonId, livreurId);

		return new ResponseEntity<Delivery>(livraison, HttpStatus.OK);
	}

	@GetMapping(path = "/unaffected")
	public List<Delivery> getUnaffectedLivraison() {
		return deliveryRepository.findUnaffected();
	}

	@GetMapping(path = "/order/{orderId}")
	public Delivery getLivraisonByOrderId(Long orderId) {
		return deliveryRepository.findByOrderId(orderId);
	}

	@GetMapping(path = "/{livraisonId}")
	public Delivery getLivraison(@PathVariable Long livraisonId) {
		return deliveryRepository.findById(livraisonId).orElseThrow();
	}

	@PostMapping
	public ResponseEntity<Delivery> createDelivery(@RequestParam long orderId, @RequestParam long ticketId) {
		return null;
	}

	@PatchMapping(path = "/{deliveryId}/couriers/{courierId}")
	public ResponseEntity<Void> noteDeliveryPickUp(@PathVariable long deliveryId, @PathVariable long courierId) {
		return null;
	}

	@PatchMapping(path = "/{deliveryId}/delivered")
	public ResponseEntity<Void> noteDeliveryDelivered(@PathVariable long deliveryId) {
		return null;
	}




	
	@PostMapping(path = "/{deliveryId}/affect/{courierId}")
	public ResponseEntity<Delivery> affectCourier(@PathVariable long deliveryId, @PathVariable long courierId) {
	
		

		return new ResponseEntity<Delivery>(deliveryService.affectLivreur(deliveryId, courierId),HttpStatus.OK);
	}
	

}
