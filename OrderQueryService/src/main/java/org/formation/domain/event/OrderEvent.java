package org.formation.domain.event;

import lombok.Data;

@Data
public class OrderEvent {

	private long orderId;
	private String status;
	
}
