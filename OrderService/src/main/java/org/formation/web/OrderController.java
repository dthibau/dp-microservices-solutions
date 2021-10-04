package org.formation.web;

import java.util.List;
import java.util.stream.Collectors;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.service.OrderService;
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
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
		
		Order order = orderService.createOrder(request);
		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
	}

	
	@GetMapping
	public List<Order> findAllOrders() {
		return orderRepository.findAll().stream().map(o -> orderRepository.fullLoad(o.getId()).orElseThrow()).collect(Collectors.toList());
	}
	
	@GetMapping(path="/{orderId}")
	public Order findOne(@PathVariable Long orderId) {
		return orderRepository.fullLoad(orderId).orElseThrow();
	}
}
