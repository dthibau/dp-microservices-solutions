package org.formation.service;

import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;

import lombok.Data;

@Data
public class ChangeStatusEvent extends TicketEvent {

	private TicketStatus oldStatus;
	private TicketStatus newStatus;
	private Long ticketId;
	
	public ChangeStatusEvent(Ticket ticket, TicketStatus oldStatus, TicketStatus newStatus) {
		super(ticket);
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
		ticketId = ticket.getId();
	}
	
}
