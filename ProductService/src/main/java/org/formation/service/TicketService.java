package org.formation.service;

import java.util.List;

import org.formation.domain.ChangeStatusEvent;
import org.formation.domain.ChangeStatusEventRepository;
import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketRepository;
import org.formation.domain.TicketStatus;
import org.formation.service.saga.CommandResponse;
import org.formation.service.saga.TicketCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Transactional
@Log
public class TicketService {

	@Value("${app.channel.order-saga}")
	String ORDER_SAGA_CHANNEL;
	
	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ChangeStatusEventRepository eventRepository;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	KafkaTemplate<Long, CommandResponse> commandResponseTemplate;

	@KafkaListener(topics = "#{'${app.channel.ticket}'}", id = "handleCreate")
	public void handleTicketCommand(TicketCommand ticketCommand) {
		switch ( ticketCommand.getCommand() ) {
		case "TICKET_CREATE" :
			handleCreateTicketCommand(ticketCommand);
			break;
		case "TICKET_APPROVE" :
			handleApproveTicketCommand(ticketCommand);
			break;
		case "TICKET_REJECT":
			handleRejectTicketCommand(ticketCommand);
			break;
		
		}
		
	}
	public void handleCreateTicketCommand(TicketCommand createTicketCommand) {
		log.info("handleCreate method");
		Ticket ticket = createTicket(createTicketCommand.getOrderId(), createTicketCommand.getProductRequest());
		commandResponseTemplate.send(ORDER_SAGA_CHANNEL, new CommandResponse(ticket.getOrderId(),0,"TICKET_CREATE"));

	}
	public void handleApproveTicketCommand(TicketCommand approveTicketCommand) {
		log.info("handleApprove Method");

		Ticket ticket = ticketRepository.findByOrderId(approveTicketCommand.getOrderId());
		ticket.setStatus(TicketStatus.CREATED);
		ticketRepository.save(ticket);
	}
	public void handleRejectTicketCommand(TicketCommand rejectTicketCommand) {
		log.info("handleReject Method");

		Ticket ticket = ticketRepository.findByOrderId(rejectTicketCommand.getOrderId());
		ticket.setStatus(TicketStatus.REJECTED);
		ticketRepository.save(ticket);
	}
	public Ticket createTicket(long orderId, List<ProductRequest> productsRequest) {
		Ticket t = new Ticket();
		t.setOrderId(orderId);
		t.setProductRequests(productsRequest);
		t.setStatus(TicketStatus.PENDING);
		
		t = ticketRepository.save(t);
		
		return t;
	}
	
	public Ticket readyToPickUp(Long ticketId) {
		
		Ticket t = ticketRepository.findById(ticketId).orElseThrow();
		ChangeStatusEvent event = new ChangeStatusEvent(t, t.getStatus(),TicketStatus.READY_TO_PICK);
		
		t.setStatus(TicketStatus.READY_TO_PICK);
		
		
		ticketRepository.save(t);
		
		eventRepository.save(event);

		return t;
		

	}
}
