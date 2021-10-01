package org.formation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.web.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	
	
	public Order createOrder(CreateOrderRequest createOrderRequest) {
		
		// Save in local DataBase
		Order order = orderRepository.save(createOrderRequest.getOrder());
		
		// Create Ticket in ProductService
		List<ProductRequest> productRequest = order.getOrderItems().stream().map(i -> new ProductRequest(i)).collect(Collectors.toList());
		String endPoint = "http://ProductService/api/products/"+order.getId();
		
		Ticket t = restTemplate.postForObject(endPoint, productRequest, Ticket.class);
		
		log.info("Ticket created " + t);
		
		return order;
	}
}
