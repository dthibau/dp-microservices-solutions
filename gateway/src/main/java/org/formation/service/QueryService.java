package org.formation.service;

import org.formation.domain.Livraison;
import org.formation.domain.Order;
import org.formation.domain.OrderDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Log

public class QueryService {

	@Resource
	WebClient orderWebClient;

	@Resource
	WebClient livraisonWebClient;

	public Mono<OrderDto> getOrderDetails(Long orderId) {

		Mono<Order> order = findOrderByOrderId(orderId);

		Mono<Livraison> livraison = findLivraisonByOrderId(orderId);

		return  Mono.zip(livraison, order)
                .map(tuple -> new OrderDto(tuple.getT2(), tuple.getT1()));
	}

	public Flux<OrderDto> getOrdersDetails() {
		
		Flux<Livraison> livraison = livraisonWebClient.get()
				.uri("/affected")
				.retrieve()
				.bodyToFlux(Livraison.class);
		
		return livraison.flatMap(l -> findOrderByOrderId(l.getOrderId())
				    .map(o -> new OrderDto(o, l)));


	}
	
	private Mono<Order> findOrderByOrderId(Long orderId) {
		return orderWebClient.get()
				.uri("/{id}",orderId)
				.retrieve()
				.bodyToMono(Order.class);
	}
	
	private Mono<Livraison> findLivraisonByOrderId(Long orderId) {
		return livraisonWebClient.get()
				.uri("/{id}",orderId)
				.retrieve()
				.bodyToMono(Livraison.class);
	}
}
