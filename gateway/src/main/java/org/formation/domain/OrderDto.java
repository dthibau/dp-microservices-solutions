package org.formation.domain;

import java.time.Instant;

import org.formation.service.OrderEvent;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

	@Id
	long id;
	
	long orderId;
	Instant date;
	private String rue;
	private String ville;
	private String codePostal;
	String nomLivreur;
	String telephoneLivreur;
	
	public OrderDto(Order order, Livraison livraison) {
		this.orderId = order.getId();
		this.date = order.getDate();
		this.rue = order.getDeliveryInformation().getAddress().getRue();
		this.ville = order.getDeliveryInformation().getAddress().getVille();
		this.codePostal = order.getDeliveryInformation().getAddress().getCodePostal();
		this.nomLivreur = livraison.getLivreur().getNom();
		this.telephoneLivreur = livraison.getLivreur().getTelephone();
		
	}
	public OrderDto(OrderEvent orderEvent) {
		this.orderId = orderEvent.getOrderId();
		this.date = orderEvent.getOrder().getDate();
		this.rue = orderEvent.getOrder().getDeliveryInformation().getAddress().getRue();
		this.ville = orderEvent.getOrder().getDeliveryInformation().getAddress().getVille();
		this.codePostal = orderEvent.getOrder().getDeliveryInformation().getAddress().getCodePostal();		
	}
}
