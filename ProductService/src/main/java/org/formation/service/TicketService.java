package org.formation.service;

import org.formation.domain.ResultDomain;
import org.formation.domain.Ticket;
import org.formation.domain.TicketRepository;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.TicketStatusEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Transactional
@Log
public class TicketService {
	@Value("${app.channel.order-status}")
	public String ORDER_STATUS_CHANNEL;
	@Value("${app.channel.ticket-status}")
	public String TICKET_STATUS_CHANNEL;


	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	TicketStatusEventRepository eventRepository;
	
	@Autowired
	EventService eventService;
	
	@KafkaListener(topics="#{'${app.channel.order-status}'}", id="ticket-service")
	public void handleOrderEvent(OrderEvent orderEvent) {
		log.info("handle ORDER_STATUS " + orderEvent.getStatus());
		ResultDomain resultDomain = null;
		switch (orderEvent.getStatus() ) {
		case "PENDING" : 
			resultDomain = new ResultDomain(ticketRepository.save(Ticket.createTicket(orderEvent)));
			break;
		case "APPROVED" :
			Ticket t =ticketRepository.findByOrderId(orderEvent.getOrderId());
			resultDomain = t.approveTicket();
			break;
		case "REJECTED" :
			t =ticketRepository.findByOrderId(orderEvent.getOrderId());
			resultDomain = t.rejectTicket();
			break;
		}

		ticketRepository.save(resultDomain.getTicket());
		eventRepository.save(resultDomain.getStatusEvent());
		
	}
	
	public Ticket readyToPickUp(Long ticketId) {
		
		Ticket t = ticketRepository.findById(ticketId).orElseThrow();	
		ResultDomain resultDomain = t.readyToPickUp();
		
		ticketRepository.save(resultDomain.getTicket());
		eventRepository.save(resultDomain.getStatusEvent());

		return resultDomain.getTicket();
		

	}
	
}
