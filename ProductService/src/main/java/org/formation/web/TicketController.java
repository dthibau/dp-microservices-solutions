package org.formation.web;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.ResultDomain;
import org.formation.domain.Ticket;
import org.formation.domain.TicketRepository;
import org.formation.domain.TicketStatus;
import org.formation.service.TicketService;
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
	TicketService ticketService;
	
	@Autowired
	TicketRepository ticketRepository;

	@GetMapping("/approved")
	public List<Ticket> findApproved() {
		return ticketRepository.findAll().stream().filter(t -> t.getStatus().equals(TicketStatus.APPROVED)).toList();
	}
	
	@PostMapping(path="/{orderId}")
	public ResponseEntity<Ticket> acceptOrder(@PathVariable Long orderId, @RequestBody List<ProductRequest> productsRequest) {
		
		
		ResultDomain resultDomain = ticketService.handleCreateTicketCommand(orderId, productsRequest);
		log.info("Instance " + port + " created a ticket "+ resultDomain.getTicket());
		
		return new ResponseEntity<Ticket>(resultDomain.getTicket(),HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/{ticketId}/pickup")
	public ResponseEntity<Ticket> noteTicketReadyToPickUp(@PathVariable Long ticketId) {
		
		Ticket t = ticketService.readyToPickUp(ticketId);

		log.info("Ticket readyToPickUp "+ t.getId());

		return new ResponseEntity<Ticket>(t,HttpStatus.CREATED);
	}

	@GetMapping(path = "/orders/{orderId}")
	public ResponseEntity<Ticket> findTicket(@PathVariable Long orderId) {
		return null;
	}
}
