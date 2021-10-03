package org.formation.web;

import org.formation.domain.OrderDto;
import org.formation.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/query/orders")
public class QueryController {

	@Autowired
	QueryService queryService;
	
	
	@GetMapping(path = "{orderId}")
	public OrderDto getOrderDetails(@PathVariable long orderId) {
		
		return queryService.getOrderDetails(orderId);
	}
}
