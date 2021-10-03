package org.formation.domain.event;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.formation.domain.TicketStatus;

import lombok.Data;

@Entity
@Data
public class TicketStatusEvent  {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id; 

	long ticketId;
	long orderId;
	Instant instant;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus newStatus;
	
	public TicketStatusEvent() {
		super();
	}
	public TicketStatusEvent(Long ticketId, Long orderId, TicketStatus newStatus) {
		this.ticketId= ticketId;
		this.orderId = orderId;
		this.newStatus = newStatus;
	}

	
}
