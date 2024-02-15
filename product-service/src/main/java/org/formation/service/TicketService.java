package org.formation.service;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;
import org.formation.domain.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService {

	private final TicketRepository ticketRepository;

	private final EventService eventService;

	public TicketService(TicketRepository ticketRepository, EventService eventService) {
		this.ticketRepository = ticketRepository;
		this.eventService = eventService;
	}

	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) {
		Ticket t = new Ticket();
		t.setOrderId("" + orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.CREATED);

		t = ticketRepository.save(t);
		TicketEvent event = new TicketEvent(null, TicketStatus.READY_TO_PICK, t);
		eventService.publish(event);

		return t;
	}

	public Ticket readyToPickUp(Long ticketId) {
		Ticket t = ticketRepository.findById(ticketId).orElseThrow();
		TicketEvent event = new TicketEvent(t.getStatus(), TicketStatus.READY_TO_PICK, t);

		t.setStatus(TicketStatus.READY_TO_PICK);
		eventService.publish(event);

		ticketRepository.save(t);

		return t;
	}
}
