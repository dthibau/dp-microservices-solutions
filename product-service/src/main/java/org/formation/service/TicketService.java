package org.formation.service;

import java.util.List;

import org.formation.domain.MaxWeightExceededException;
import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
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

	public Ticket createTicket(Long orderId, List<ProductRequest> productsRequest) throws JsonProcessingException, MaxWeightExceededException {
		
		ResultDomain resultDomain = Ticket.createTicket(orderId, productsRequest);

		ticketRepository.save(resultDomain.getTicket());
		ticketEventRepository.save(resultDomain.getTicketEvent());

		return resultDomain.getTicket();
	}
	
	public Ticket approveTicket(Long orderId) throws JsonProcessingException {
		Ticket ticket = ticketRepository.findByOrderId(orderId).orElseThrow(() -> new EntityNotFoundException("No corresponfing ticket for orderId=" + orderId));
		ResultDomain resultDomain = ticket.approveTicket();
		
		ticketRepository.save(resultDomain.getTicket());
		ticketEventRepository.save(resultDomain.getTicketEvent());

		return resultDomain.getTicket();
	}
	
	public Ticket rejectTicket(Long orderId) throws JsonProcessingException {
		Ticket ticket = ticketRepository.findByOrderId(orderId).orElseThrow(() -> new EntityNotFoundException("No corresponfing ticket for orderId=" + orderId));;
		ResultDomain resultDomain = ticket.rejectTicket();
		
		ticketRepository.save(resultDomain.getTicket());
		ticketEventRepository.save(resultDomain.getTicketEvent());

		return resultDomain.getTicket();
	}

	public Ticket readyToPickUp(Long ticketId) throws JsonProcessingException {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
		ResultDomain resultDomain = ticket.readyToPickUp();
		
		ticketRepository.save(resultDomain.getTicket());
		ticketEventRepository.save(resultDomain.getTicketEvent());

		return resultDomain.getTicket();
	}
}
