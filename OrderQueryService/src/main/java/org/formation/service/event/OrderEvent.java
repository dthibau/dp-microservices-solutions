package org.formation.service.event;

import lombok.Data;

@Data
public class OrderEvent {

	private long orderId;
	private String status;
	
}
