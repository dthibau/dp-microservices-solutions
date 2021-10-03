package org.formation.web;

import java.util.List;

import org.formation.domain.Courier;
import org.formation.domain.CourierRepository;
import org.formation.domain.Delivery;
import org.formation.domain.DeliveryRepository;
import org.formation.domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping
	public List<Delivery> findDeliveries(@RequestParam(required = false) String status) {
		return deliveryRepository.findAll();
	}

	@GetMapping(path = "/orders/{orderId}")
	public Delivery findDeliveryByOrderId(@PathVariable long orderId) {
		return null;
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


	
	@PostMapping(path = "/couriers/{courierId}/position")
	public ResponseEntity<Void> updatePosition(@PathVariable long courierId, @RequestBody Position position) {
		Courier courier = courierRepository.findById(courierId).orElseThrow();
		courier.setPosition(position);
		courierRepository.save(courier);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}


	
	@PostMapping(path = "/{deliveryId}/affect/{courierId}")
	public ResponseEntity<Delivery> affectCourier(@PathVariable long livraisonId, @PathVariable long courierId) {
		
		Delivery delivery = deliveryRepository.findById(livraisonId).orElseThrow();
		Courier courier = courierRepository.findById(courierId).orElseThrow();
		delivery.setLivreur(courier);
		deliveryRepository.save(delivery);
		return new ResponseEntity<Delivery>(delivery,HttpStatus.OK);
	}
	
}
