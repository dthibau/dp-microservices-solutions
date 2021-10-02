package org.formation.service;

import org.formation.domain.Order;
import org.formation.saga.CreateOrderSaga;
import org.formation.web.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class OrderService {

	
	
	@Autowired
	CreateOrderSaga createOrderSaga;

	
	public Order createOrder(CreateOrderRequest createOrderRequest) {
		
		// Save in local DataBase
		Order order = createOrderSaga.startSaga(createOrderRequest.getOrder());
		
		log.info("SAGA created " + order.getId());
		
		return order;
	}
	
	
}
