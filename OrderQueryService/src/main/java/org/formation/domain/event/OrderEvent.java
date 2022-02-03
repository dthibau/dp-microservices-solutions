package org.formation.domain.event;

import lombok.Data;

@Data
public class OrderEvent {

	long orderId;
	private String status;
}
