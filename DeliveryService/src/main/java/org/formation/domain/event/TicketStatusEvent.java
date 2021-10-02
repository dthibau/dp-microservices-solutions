package org.formation.domain.event;

import lombok.Data;

@Data
public class TicketStatusEvent {

	private Long ticketId;
	
	private Long orderId;
	
	private String oldStatus;
	private String newStatus;
	
	
}
