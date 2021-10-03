package org.formation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.service.event.OrderEvent;
import org.formation.service.saga.CreateOrderSaga;
import org.formation.web.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class OrderService {

	@Value("${app.channel.order-event}")
	String ORDER_STATUS_CHANNEL;
	
	@Autowired
	KafkaTemplate<Long, OrderEvent> kafkaOrderTemplate;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	private CreateOrderSaga createOrderSaga;
	
	public Order createOrder(CreateOrderRequest createOrderRequest) {
		
		// Save in local DataBase
		Order order = orderRepository.save(createOrderRequest.getOrder());
		
		List<ProductRequest> productRequest = order.getOrderItems().stream().map(i -> new ProductRequest(i)).toList();

		OrderEvent event = new OrderEvent(order.getId(), productRequest, order.getStatus());
		
		kafkaOrderTemplate.send(ORDER_STATUS_CHANNEL,event);
		
		// Starting SAGA
		createOrderSaga.startSaga(order);

		
		
		return order;
	}

}
