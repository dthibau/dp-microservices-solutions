package org.formation.service.event;

import java.util.List;

import org.formation.domain.OrderStatus;
import org.formation.service.ProductRequest;

import lombok.Data;

@Data
public class OrderEvent {

	private long orderId;
	private List<ProductRequest> productRequest;
	private OrderStatus status;
	
	public OrderEvent(long orderId, List<ProductRequest> productRequest, OrderStatus status) {
		super();
		this.orderId = orderId;
		this.productRequest = productRequest;
		this.status = status;
	}
}
