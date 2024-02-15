package org.formation.service;

import java.util.List;

import org.formation.domain.Order;
import org.formation.domain.repository.OrderRepository;
import org.formation.web.CreateOrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;

@Service
@Transactional
@Log
public class OrderService {

	
	private final RestTemplate productRestTemplate;

	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository, RestTemplate productRestTemplate ) {
		this.orderRepository = orderRepository;
		this.productRestTemplate = productRestTemplate;
	}


	public Order createOrder(CreateOrderRequest createOrderRequest) {
			
			// Save in local DataBase
			Order order = orderRepository.save(createOrderRequest.getOrder());
			
			// Create Ticket in ProductService
			List<ProductRequest> productRequest = order.getOrderItems().stream().map(i -> new ProductRequest(i)).toList();
			
			String endPoint = "/api/tickets/{oderId}";
			
			Ticket t = productRestTemplate.postForObject(endPoint, productRequest, Ticket.class, order.getId());
			
			log.info("Ticket created " + t);
			
			return order;
		}
}
