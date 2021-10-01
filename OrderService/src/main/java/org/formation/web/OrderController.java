package org.formation.web;

import org.formation.domain.Order;
import org.formation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
		
		Order order = orderService.createOrder(request);
		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
	}
}
