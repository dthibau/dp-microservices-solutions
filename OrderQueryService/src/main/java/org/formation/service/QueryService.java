package org.formation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.formation.domain.Livraison;
import org.formation.domain.Order;
import org.formation.domain.OrderDtORepository;
import org.formation.domain.OrderDto;
import org.formation.service.event.DeliveryEvent;
import org.formation.service.event.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;


@Service
@Log
public class QueryService {

	@Resource
	RestTemplate orderRestTemplate;
	
	@Resource
	RestTemplate livraisonRestTemplate;
	
	@Autowired
	OrderDtORepository orderDtoRepository;
	
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
	
	@KafkaListener(topics="#{'${app.channel.order-event}'}", id="query-order")
	public void handleOrderEvent(OrderEvent orderEvent) {
		
		OrderDto orderDto = new OrderDto();
		orderDto.setOrderId(orderEvent.getOrderId());
		orderDtoRepository.save(orderDto);
	}
	
	@KafkaListener(topics="#{'${app.channel.delivery-event}'}", id="query-delivery")
	public void handleDeliveryEvent(DeliveryEvent deliveryEvent) {
		
		if ( deliveryEvent.getStatus().equals("AFFECTE") ) {
			OrderDto orderDto = orderDtoRepository.findById(deliveryEvent.getLivraison().getOrderId()).orElseThrow();
			orderDto.setNomLivreur(deliveryEvent.getLivraison().getLivreur().getNom());
			orderDto.setTelephoneLivreur(deliveryEvent.getLivraison().getLivreur().getTelephone());
			orderDtoRepository.save(orderDto);
		}
		
				
		
	}
}
