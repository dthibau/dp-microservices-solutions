package org.formation.domain.event;

import java.time.Instant;

import lombok.Data;

@Data
public class TicketStatusEvent {


	private long ticketId;
	private long orderId;
	Instant instant;

	
	private String oldStatus;
	private String newStatus;
	
	public TicketStatusEvent() {
		super();
	}

	
}
