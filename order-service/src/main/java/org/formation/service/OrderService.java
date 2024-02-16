package org.formation.service;

import org.formation.domain.Order;
import org.formation.domain.repository.OrderRepository;
import org.formation.service.saga.CreateOrderSaga;
import org.formation.web.CreateOrderRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Transactional
@Log
public class OrderService {


	
	private final OrderRepository orderRepository;

	private final CreateOrderSaga createOrderSaga;
	
	

	public OrderService(OrderRepository orderRepository,  CreateOrderSaga createOrderSaga, KafkaTemplate<Long, OrderEvent> kafkaTemplate) {
		this.orderRepository = orderRepository;
		this.createOrderSaga = createOrderSaga;
	}

	public Order createOrder(CreateOrderRequest createOrderRequest) {

		// Save in local DataBase
		Order order = orderRepository.save(createOrderRequest.getOrder());

		createOrderSaga.startSaga(order);
		
		
		

		
		return order;
	}


}
