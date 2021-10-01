package org.formation.web;

import org.formation.domain.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
		
		return null;
	}
}
