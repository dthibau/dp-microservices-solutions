package org.formation.service;

import org.formation.domain.Livraison;

import lombok.Data;

@Data
public class LivraisonEvent {

	private Livraison livraison;

	long orderId;

	private String status;

	public LivraisonEvent() {
		super();
	}

	public LivraisonEvent(Livraison livraison, String status) {
		super();
		this.livraison = livraison;
		this.orderId = livraison.getOrderId();
		this.status = status;
	}
}
