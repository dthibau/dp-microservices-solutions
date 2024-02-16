package org.formation.service;

import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEvent {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id; 
		
	@Enumerated(EnumType.STRING)	
	private TicketStatus oldStatus;
	@Enumerated(EnumType.STRING)	
	private TicketStatus newStatus;
	
	private Long ticketId; 
	
	private String ticketPayload;
	

}
