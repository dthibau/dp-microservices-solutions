package org.formation.service.event;

import org.formation.domain.Livraison;

import lombok.Data;

@Data
public class DeliveryEvent {

	private Livraison livraison;
	
	private String status;

	public DeliveryEvent() {
		super();
	}
	public DeliveryEvent(Livraison livraison, String status) {
		super();
		this.livraison = livraison;
		this.status = status;
	}
	
	
}
