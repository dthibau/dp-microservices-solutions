package org.formation.domain.event;

import org.formation.domain.Livraison;

import lombok.Data;

@Data
public class DeliveryEvent {

	Livraison livraison;
	
	String status;
	
	
}
