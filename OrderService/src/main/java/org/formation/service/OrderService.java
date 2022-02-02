package org.formation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.service.saga.CreateOrderSaga;
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
	private CreateOrderSaga createOrderSaga;
	
	public Order createOrder(CreateOrderRequest createOrderRequest) {
		
		// Save in local DataBase
		Order order = orderRepository.save(createOrderRequest.getOrder());
		
		// Create Ticket in ProductService
		createOrderSaga.publishTicketCreatedEvent(order);
		
		
		log.info("Publish Ticket created Command for order " + order);
		
		return order;
	}
	

}
