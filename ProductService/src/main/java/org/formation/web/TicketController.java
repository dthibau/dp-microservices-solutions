package org.formation.web;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketRepository;
import org.formation.domain.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	Integer port;
	
	@Autowired
	TicketRepository ticketRepository;

	@PostMapping("/{orderId}")
	public ResponseEntity<Ticket> createTicket(@PathVariable Long orderId, @RequestBody List<ProductRequest> productsRequest) {
		Ticket t = new Ticket();
		t.setOrderId(""+orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.CREATED);
		
		t = ticketRepository.save(t);
		
		log.info("Instance " + port + " created a ticket "+ t);
		
		return new ResponseEntity<Ticket>(t,HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/{ticketId}/pickup")
	public ResponseEntity<Ticket> noteTicketReadyToPickUp(@PathVariable Long ticketId) {
		return null;
	}

	@GetMapping(path = "/orders/{orderId}")
	public ResponseEntity<Ticket> findTicket(@PathVariable Long orderId) {
		return null;
	}
}
