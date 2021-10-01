package org.formation.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

	@PostMapping(path = "/pick/{deliveryId}")
	public ResponseEntity<Void> noteDeliveryPickedUp(@PathVariable long deliveryId) {
		return null;
	}
	
	@PostMapping(path = "/position")
	public ResponseEntity<Void> updatePosition(@RequestBody Position position) {
		return null;
	}

	@PostMapping(path = "/delivered/{deliveryId}")
	public ResponseEntity<Void> noteDeliveryDelivered(@PathVariable long deliveryId) {
		return null;
	}
	
}
