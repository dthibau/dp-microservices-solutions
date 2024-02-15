package org.formation.service;

import java.util.List;

import org.formation.domain.Order;
import org.formation.domain.repository.OrderRepository;
import org.formation.web.CreateOrderRequest;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
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

	private CircuitBreakerFactory cbFactory;

	public OrderService(OrderRepository orderRepository, RestTemplate productRestTemplate,
			CircuitBreakerFactory cbFactory) {
		this.orderRepository = orderRepository;
		this.productRestTemplate = productRestTemplate;
		this.cbFactory = cbFactory;
	}

	public Order createOrder(CreateOrderRequest createOrderRequest) {

		// Save in local DataBase
		Order order = orderRepository.save(createOrderRequest.getOrder());

		Ticket t = _createTicket(order);
		
		log.info("Ticket created " + t);

		return order;
	}

	private Ticket _createTicket(Order order) {
		List<ProductRequest> productRequest = order.getOrderItems().stream().map(i -> new ProductRequest(i)).toList();
		String endPoint = "/api/tickets/{oderId}";

		return cbFactory.create("createTicket").run(
				() -> productRestTemplate.postForObject(endPoint, productRequest, Ticket.class, order.getId()), t -> {
					log.warning("FALLBACK " + t);
					return null;
				});

	}
}
