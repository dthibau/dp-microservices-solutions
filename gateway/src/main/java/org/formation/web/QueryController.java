package org.formation.web;

import org.formation.domain.OrderDto;
import org.formation.domain.repository.OrderDtoRepository;
import org.formation.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders/query")
public class QueryController {

	
		@Autowired
		QueryService queryService;
		
		@Autowired
		OrderDtoRepository orderDtoRepository;
		
		
		@GetMapping(path = "/{orderId}")
		public Mono<OrderDto> getOrderDetails(@PathVariable long orderId) {
			
			return orderDtoRepository.findById(orderId);
		}
		
		@GetMapping()
		public Flux<OrderDto> getOrdersDetails() {
			
			return orderDtoRepository.findAll();
		}
	
}
