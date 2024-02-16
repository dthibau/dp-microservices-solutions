package org.formation.service;

import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class ResultDomain {
	Ticket ticket;
		
	TicketEvent ticketEvent;
	
		ObjectMapper mapper = new ObjectMapper();
		
		public ResultDomain(TicketStatus oldStatus, Ticket ticket) {
			this.ticket = ticket;
			String payload = "";
			try {
				payload = mapper.writeValueAsString(ticket);
			} catch ( JsonProcessingException e) {
				e.printStackTrace();
			}
			this.ticketEvent = new TicketEvent(null,oldStatus, ticket.getStatus(),ticket.getId(),payload);
		}
}
