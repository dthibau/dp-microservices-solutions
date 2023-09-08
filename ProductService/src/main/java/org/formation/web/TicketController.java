package org.formation.web;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

	@PostMapping("/{orderId}")
	public ResponseEntity<Ticket> createTicket(@PathVariable Long orderId, @RequestBody List<ProductRequest> productsRequest) {
		return null;
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
