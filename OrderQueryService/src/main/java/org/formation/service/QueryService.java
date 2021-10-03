package org.formation.service;

import javax.annotation.Resource;

import org.formation.domain.Livraison;
import org.formation.domain.Order;
import org.formation.domain.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class QueryService {

	@Resource
	RestTemplate orderRestTemplate;
	
	@Resource
	RestTemplate livraisonRestTemplate;
	
	
	public OrderDto getOrderDetails(Long orderId) {
		
		Order order = orderRestTemplate.getForObject("/"+orderId, Order.class);
		
		Livraison livraison = livraisonRestTemplate.getForObject("/"+orderId, Livraison.class);
		
		return new OrderDto(order,livraison);
	}
}
