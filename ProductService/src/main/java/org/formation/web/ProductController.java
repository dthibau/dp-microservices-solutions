package org.formation.web;

import org.formation.domain.Ticket;
import org.formation.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/api/products")
@Log
public class ProductController {
	

	@Autowired
	TicketService ticketService;

	
	@PostMapping(path = "/tickets/{ticketId}/pickup")
	public ResponseEntity<Ticket> noteTicketReadyToPickUp(@PathVariable Long ticketId) {
		
		Ticket t = ticketService.readyToPickUp(ticketId);

		log.info("Ticket readyToPickUp "+ t.getId());

		return new ResponseEntity<Ticket>(t,HttpStatus.CREATED);
	}
}
