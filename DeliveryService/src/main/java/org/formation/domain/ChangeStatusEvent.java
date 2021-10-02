package org.formation.domain;

import lombok.Data;

@Data
public class ChangeStatusEvent {

	private Long ticketId;
	
	private Long orderId;
	
	private String oldStatus;
	private String newStatus;
	
	
}
