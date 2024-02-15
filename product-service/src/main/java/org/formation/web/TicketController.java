package org.formation.web;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;
import org.formation.domain.repository.TicketRepository;
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
	
	private final TicketRepository ticketRepository;
	
	public TicketController(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@PostMapping(path="/{orderId}")
 	public ResponseEntity<Ticket> acceptOrder(@PathVariable Long orderId, @RequestBody List<ProductRequest> productsRequest) {
		
		Ticket t = new Ticket();
		t.setOrderId(""+orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.CREATED);
		
		t = ticketRepository.save(t);
		
		log.info("Ticket created "+ t);
		
		return new ResponseEntity<Ticket>(t,HttpStatus.CREATED);
 	}
	
	@PostMapping(path = "/tickets/{ticketId}/pickup")
	public ResponseEntity<Ticket> noteTicketReadyToPickUp(@PathVariable Long ticketId) {
		return null;
	}
}
