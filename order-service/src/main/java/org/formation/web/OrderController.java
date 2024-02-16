package org.formation.web;

import java.util.List;

import org.formation.domain.Order;
import org.formation.domain.repository.OrderRepository;
import org.formation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Order createOrder(@RequestBody CreateOrderRequest request) {
		
		return orderService.createOrder(request);
	}
	
	@GetMapping
	public List<Order> findAll() {
		return orderRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Order findById(@PathVariable long id) {
		return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
}
