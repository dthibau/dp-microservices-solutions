package org.formation.service;

import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEvent {

	private TicketStatus oldStatus;
	private TicketStatus newStatus;
	
	private Ticket ticket;
	

}
