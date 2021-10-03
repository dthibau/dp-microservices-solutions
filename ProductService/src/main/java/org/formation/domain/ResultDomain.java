package org.formation.domain;

import org.formation.service.event.TicketStatusEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class ResultDomain {

	Ticket ticket;
	
	TicketStatusEvent ticketStatusEvent;

	ObjectMapper mapper = new ObjectMapper();
	
	public ResultDomain(Ticket ticket) {
		this.ticket = ticket;
		String payload = "";
		try {
			payload = mapper.writeValueAsString(ticket);
		} catch ( JsonProcessingException e) {
			e.printStackTrace();
		}
		this.ticketStatusEvent = new TicketStatusEvent(ticket.getId(),ticket.getOrderId(), ticket.getStatus(),payload);
	}
}
