package org.formation.domain.event;

import java.util.List;

import org.formation.domain.ProductRequest;

import lombok.Data;

@Data
public class OrderEvent {

	private long orderId;
	private List<ProductRequest> productRequest;
	private String status;
	

	
	
}
