package org.formation.service;

import lombok.Data;

@Data
public class TicketEvent {

	private Ticket ticket;
		
		
	private String newStatus;
}
