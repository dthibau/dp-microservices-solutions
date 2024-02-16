package org.formation.service;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketStatus;
import org.formation.domain.repository.TicketEventRepository;
import org.formation.domain.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class TicketService {

	@Autowired
	ObjectMapper mapper;
	
	private final TicketRepository ticketRepository;

	private final TicketEventRepository ticketEventRepository;

	public TicketService(TicketRepository ticketRepository, TicketEventRepository ticketEventRepository) {
		this.ticketRepository = ticketRepository;
		this.ticketEventRepository = ticketEventRepository;
	}

	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) throws JsonProcessingException {
		Ticket t = new Ticket();
		t.setOrderId(orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.PENDING);

		t = ticketRepository.save(t);
		TicketEvent event = new TicketEvent(null,null, TicketStatus.PENDING, t.getId(), mapper.writeValueAsString(t));
		ticketEventRepository.save(event);

		return t;
	}
	
	public Ticket approveTicket(Long orderId) throws JsonProcessingException {
		Ticket ticket = ticketRepository.findByOrderId(orderId).orElseThrow(() -> new EntityNotFoundException("No corresponfing ticket for orderId=" + orderId));
		ticket.setStatus(TicketStatus.CREATED);
		ticketRepository.save(ticket);
		TicketEvent event = new TicketEvent(null,null, TicketStatus.CREATED, ticket.getId(), mapper.writeValueAsString(ticket));
		ticketEventRepository.save(event);
		
		return ticket;
	}
	
	public Ticket rejectTicket(Long orderId) throws JsonProcessingException {
		Ticket ticket = ticketRepository.findByOrderId(orderId).orElseThrow(() -> new EntityNotFoundException("No corresponfing ticket for orderId=" + orderId));;
		ticket.setStatus(TicketStatus.REJECTED);
		ticketRepository.save(ticket);
		TicketEvent event = new TicketEvent(null,null, TicketStatus.REJECTED, ticket.getId(), mapper.writeValueAsString(ticket));
		ticketEventRepository.save(event);
		
		return ticket;
	}

	public Ticket readyToPickUp(Long ticketId) throws JsonProcessingException {
		Ticket t = ticketRepository.findById(ticketId).orElseThrow();
		TicketEvent event = new TicketEvent(null,t.getStatus(), TicketStatus.READY_TO_PICK, t.getId(), mapper.writeValueAsString(t));

		t.setStatus(TicketStatus.READY_TO_PICK);
		ticketEventRepository.save(event);

		ticketRepository.save(t);

		return t;
	}
}
