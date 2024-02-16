package org.formation.domain;

import lombok.Data;

@Data
public class Livraison {

	Long orderId;
	Livreur livreur;
}
