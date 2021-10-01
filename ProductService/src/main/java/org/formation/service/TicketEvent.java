package org.formation.service;

import java.time.Instant;

import org.formation.domain.Ticket;

import lombok.Data;

@Data
public class TicketEvent {

	Ticket ticket;
	Instant instant;
	
	public TicketEvent(Ticket ticket) {
		super();
		this.ticket = ticket;
		instant = Instant.now();
	}
	
}
