package org.formation.service;

import org.formation.domain.Order;
import org.formation.domain.repository.OrderRepository;
import org.formation.service.notification.Courriel;
import org.formation.service.saga.CreateOrderSaga;
import org.formation.web.CreateOrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;

@Service
@Transactional
@Log
public class OrderService {

	@Resource 
	RestTemplate notificationRestTemplate;
	
	private final OrderRepository orderRepository;

	private final CreateOrderSaga createOrderSaga;
	
	

	public OrderService(OrderRepository orderRepository,  CreateOrderSaga createOrderSaga) {
		this.orderRepository = orderRepository;
		this.createOrderSaga = createOrderSaga;
	}

	public Order createOrder(CreateOrderRequest createOrderRequest) {

		// Save in local DataBase
		Order order = orderRepository.save(createOrderRequest.getOrder());

		createOrderSaga.startSaga(order);
		
		String ret = notificationRestTemplate.postForObject("/sendSimple", 
				Courriel.builder().subject("Commande " + order.getId()).to("toto@gmail.com").text("Félicitations pour votre commande").build(), String.class);
		
		log.info(ret);
		

		
		return order;
	}


}
