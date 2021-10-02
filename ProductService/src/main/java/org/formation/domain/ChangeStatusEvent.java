package org.formation.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.formation.service.TicketEvent;

import lombok.Data;

@Entity
@Data
public class ChangeStatusEvent extends TicketEvent {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id; 
	
	@Enumerated(EnumType.STRING)
	private TicketStatus oldStatus;
	@Enumerated(EnumType.STRING)
	private TicketStatus newStatus;
	private Long ticketId;
	
	public ChangeStatusEvent() {
		super();
	}
	public ChangeStatusEvent(Ticket ticket, TicketStatus oldStatus, TicketStatus newStatus) {
		super(ticket);
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
		ticketId = ticket.getId();
	}
	
}
