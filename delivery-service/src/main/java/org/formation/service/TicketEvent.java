package org.formation.service;

import lombok.Data;

@Data
public class TicketEvent {


	private String newStatus;
	
	private Long ticketId; 
	
	private Long orderId; 
	
	private String ticketPayload;
}
