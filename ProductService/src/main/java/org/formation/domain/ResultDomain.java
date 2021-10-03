package org.formation.domain;

import org.formation.domain.event.TicketStatusEvent;

import lombok.Data;

@Data
public class ResultDomain {

	Ticket ticket;
	
	TicketStatusEvent statusEvent;
	
	public ResultDomain(Ticket ticket) {
		this.ticket = ticket;
		this.statusEvent = new TicketStatusEvent(ticket.getId(),ticket.getOrderId(),ticket.getStatus());
	}
}
