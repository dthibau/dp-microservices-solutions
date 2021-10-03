package org.formation.domain;

import lombok.Data;

@Data
public class Livraison {

	long orderId;
	Livreur livreur;
}
