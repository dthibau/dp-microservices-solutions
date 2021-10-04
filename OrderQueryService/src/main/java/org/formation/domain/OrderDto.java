package org.formation.domain;

import java.time.Instant;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class OrderDto {

	@Id
	long orderId;
	Instant date;
	@Embedded
	Address address;
	String nomLivreur;
	String telephoneLivreur;
	
	public OrderDto() {
		super();
	}
	public OrderDto(Order order, Livraison livraison) {
		this.orderId = order.getId();
		this.date = order.getDate();
		this.address = order.getDeliveryInformation().getAddress();
		this.nomLivreur = livraison.getLivreur() != null ? livraison.getLivreur().getNom() : null;
		this.telephoneLivreur = livraison.getLivreur() != null ? livraison.getLivreur().getTelephone() : null;
		
	}
}
