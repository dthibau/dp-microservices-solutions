package org.formation.web;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;
import org.formation.service.TicketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/api/tickets")
@Log
public class TicketController {
	
	@Value("${server.port}")
	int port;
	
	private final TicketService ticketService;
	
	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@PostMapping(path="/{orderId}")
 	public ResponseEntity<Ticket> acceptOrder(@PathVariable Long orderId, @RequestBody List<ProductRequest> productsRequest) {
		
		Ticket t = ticketService.createTicket(orderId, productsRequest);
		
		log.info("Ticket created "+ t + " by Instance " + port);
		
		return new ResponseEntity<Ticket>(t,HttpStatus.CREATED);
 	}
	
	@PostMapping(path = "/{ticketId}/pickup")
	public ResponseEntity<Ticket> noteTicketReadyToPickUp(@PathVariable Long ticketId) {
		
		Ticket t = ticketService.readyToPickUp(ticketId);
		
		log.info("Ticket readyToPickUp "+ t.getId());
		
		return new ResponseEntity<Ticket>(t,HttpStatus.OK);
	}
}
