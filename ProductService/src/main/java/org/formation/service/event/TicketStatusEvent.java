package org.formation.service.event;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;

import lombok.Data;


@Entity
@Data
public class TicketStatusEvent {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id; 

	long ticketId;
	
	long orderId;
	
	Instant instant;
	
	@Enumerated(EnumType.STRING)
 	private TicketStatus status;
	
	String payload;
	
	public TicketStatusEvent() {
		super();
	}
	public TicketStatusEvent(Long ticketId, Long orderId, TicketStatus status, String payload) {
		super();
		if ( ticketId != null )
			this.ticketId = ticketId;
		this.orderId = orderId;
		this.status = status;
		this.payload = payload;
		instant = Instant.now();
	}
	
}
