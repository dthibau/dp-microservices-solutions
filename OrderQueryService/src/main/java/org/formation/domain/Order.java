package org.formation.domain;

import java.time.Instant;

import lombok.Data;

@Data
public class Order {
	private long orderId;
	private Instant date;
	
	DeliveryInformation deliveryInformation;
	
}
