package org.formation.web;

import org.formation.domain.Delivery;
import org.formation.domain.Position;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

	@GetMapping
	public List<Delivery> findDeliveries(@RequestParam(required = false) String status) {
		return null;
	}

	@GetMapping(path = "/{orderId}")
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

}
