package org.formation.service;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketRepository;
import org.formation.domain.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketService {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	EventService eventService;
	
	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) {
		Ticket t = new Ticket();
		t.setOrderId(""+orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.CREATED);
		
		t = ticketRepository.save(t);
		
		return t;
	}
	
	public Ticket readyToPickUp(Long ticketId) {
		
		Ticket t = ticketRepository.findById(ticketId).orElseThrow();
		ChangeStatusEvent event = new ChangeStatusEvent(t, t.getStatus(),TicketStatus.READY_TO_PICK);
		
		t.setStatus(TicketStatus.READY_TO_PICK);
		eventService.notify(event);
		
		ticketRepository.save(t);
		
		return t;
		

	}
}
