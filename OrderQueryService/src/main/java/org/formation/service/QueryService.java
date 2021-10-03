package org.formation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	public List<OrderDto> getOrdersDetails() {
		Order[] orders = orderRestTemplate.getForObject("/", Order[].class);
		
		List<OrderDto> ret = new ArrayList<>();
		
		Arrays.stream(orders).forEach(o -> {
			Livraison livraison = livraisonRestTemplate.getForObject("/"+o.getId(), Livraison.class);
			ret.add(new OrderDto(o,livraison));
		});

		return ret;
	}
}
