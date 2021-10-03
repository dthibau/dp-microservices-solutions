package org.formation.domain;

import java.time.Instant;

import lombok.Data;

@Data
public class OrderDto {

	long orderId;
	Instant date;
	Address address;
	String nomLivreur;
	String telephoneLivreur;
	
	public OrderDto(Order order, Livraison livraison) {
		this.orderId = order.getId();
		this.date = order.getDate();
		this.address = order.getDeliveryInformation().getAddress();
		this.nomLivreur = livraison.getLivreur().getNom();
		this.telephoneLivreur = livraison.getLivreur().getTelephone();
		
	}
}
