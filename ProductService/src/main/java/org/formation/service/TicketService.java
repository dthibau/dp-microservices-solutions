package org.formation.service;

import java.util.List;

import org.formation.domain.MaxWeightExceededException;
import org.formation.domain.ProductRequest;
import org.formation.domain.ResultDomain;
import org.formation.domain.Ticket;
import org.formation.domain.TicketRepository;
import org.formation.service.event.TicketStatusEvent;
import org.formation.service.event.TicketStatusEventRepository;
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
	TicketStatusEventRepository eventRepository;
	

	
	@Autowired
	KafkaTemplate<Long, CommandResponse> commandResponseTemplate;

	@KafkaListener(topics = "#{'${app.channel.ticket-command}'}", id = "ticket-service")
	public void handleTicketCommand(TicketCommand ticketCommand) {
		log.info("Receiving command : " + ticketCommand);
		ResultDomain resultDomain = null;
		switch ( ticketCommand.getCommand() ) {
		case "TICKET_CREATE" :
			resultDomain = handleCreateTicketCommand(ticketCommand.getOrderId(), ticketCommand.getProductRequest());
			break;
		case "TICKET_APPROVE" :
			resultDomain = handleApproveTicketCommand(ticketCommand);
			break;
		case "TICKET_REJECT":
			resultDomain = handleRejectTicketCommand(ticketCommand);
			break;		
		}
		if ( resultDomain != null )
			_storeResultDomain(resultDomain);
		
	}
	public ResultDomain handleCreateTicketCommand(long orderId, List<ProductRequest> productRequest) {
		ResultDomain ret = null;
		try {
			ret = Ticket.createTicket( orderId, productRequest);
			commandResponseTemplate.send(ORDER_SAGA_CHANNEL, new CommandResponse(orderId,0,"TICKET_CREATE"));
		} catch (MaxWeightExceededException e) {
			commandResponseTemplate.send(ORDER_SAGA_CHANNEL, new CommandResponse(orderId,-1,"TICKET_CREATE"));
		}
		
		return ret;

	}
	public ResultDomain handleApproveTicketCommand(TicketCommand approveTicketCommand) {

		Ticket ticket = ticketRepository.findByOrderId(approveTicketCommand.getOrderId());
		return ticket.approveTicket();
		
	}
	public ResultDomain handleRejectTicketCommand(TicketCommand rejectTicketCommand) {

		Ticket ticket = ticketRepository.findByOrderId(rejectTicketCommand.getOrderId());
		return ticket.rejectTicket();
	}
	
	
	public Ticket readyToPickUp(Long ticketId) {
		
		Ticket t = ticketRepository.findById(ticketId).orElseThrow();
		ResultDomain resultDomain = t.readyToPickUp();
		_storeResultDomain(resultDomain);
		
		return resultDomain.getTicket();
		
	}
	
	private void _storeResultDomain(ResultDomain resultDomain) {
		Ticket t = ticketRepository.save(resultDomain.getTicket());
		TicketStatusEvent ticketStatusEvent = resultDomain.getTicketStatusEvent();
		ticketStatusEvent.setTicketId(t.getId());
		eventRepository.save(ticketStatusEvent);
	}
}
