package org.formation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.web.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;

@Service
@Log
public class OrderService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	public Order createOrder(CreateOrderRequest createOrderRequest) {
		
		// Save in local DataBase
		Order order = orderRepository.save(createOrderRequest.getOrder());
		
		// Create Ticket in ProductService
		Ticket t = _createTicket(order);
		
		
		log.info("Ticket created " + t);
		
		return order;
	}
	
	private Ticket _createTicket(Order order) {
		List<ProductRequest> productRequest = order.getOrderItems().stream().map(i -> new ProductRequest(i)).collect(Collectors.toList());
		String endPoint = "http://ProductService/api/products/"+order.getId();
		
		
		return cbFactory.create("sendsimple").run(
				() -> restTemplate.postForObject(endPoint, productRequest, Ticket.class),
				t -> {log.warning("FALLBACK "+t); return null;}
				);
		
	}
}
