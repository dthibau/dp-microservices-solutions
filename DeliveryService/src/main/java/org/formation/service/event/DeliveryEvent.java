package org.formation.service.event;

import org.formation.domain.Delivery;

import lombok.Data;

@Data
public class DeliveryEvent {

	private Delivery livraison;
	
	long orderId;

	
	private String status;

	public DeliveryEvent() {
		super();
	}
	public DeliveryEvent(Delivery livraison, String status) {
		super();
		this.livraison = livraison;
		this.orderId = livraison.getOrderId();
		this.status = status;
	}
	
	
}
