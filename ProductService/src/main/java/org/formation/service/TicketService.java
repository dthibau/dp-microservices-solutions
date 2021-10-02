package org.formation.service;

import java.util.List;

import org.formation.domain.ProductRequest;
import org.formation.domain.Ticket;
import org.formation.domain.TicketRepository;
import org.formation.domain.TicketStatus;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.TicketStatusEvent;
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
		String status = orderEvent.getStatus();
		Ticket t = null;
		TicketStatus oldStatus = null;
		switch (status ) {
		case "PENDING" : 
			t = _createTicket(orderEvent.getOrderId(), orderEvent.getProductRequest());
			break;
		case "APPROVED" :
			t =ticketRepository.findByOrderId(orderEvent.getOrderId());
			oldStatus = t.getStatus();
			t.setStatus(TicketStatus.APPROVED);
			ticketRepository.save(t);
			break;
		case "REJECTED" :
			t =ticketRepository.findByOrderId(orderEvent.getOrderId());
			oldStatus = t.getStatus();
			t.setStatus(TicketStatus.REJECTED);
			ticketRepository.save(t);
			break;
		}
		
		TicketStatusEvent event = new TicketStatusEvent(t.getId(),t.getOrderId(),oldStatus,t.getStatus());
		eventRepository.save(event);
		
	}
	
	public Ticket readyToPickUp(Long ticketId) {
		
		Ticket t = ticketRepository.findById(ticketId).orElseThrow();
		TicketStatusEvent event = new TicketStatusEvent(t.getId(), t.getOrderId(), t.getStatus(),TicketStatus.READY_TO_PICK);
		
		t.setStatus(TicketStatus.READY_TO_PICK);
		
		
		ticketRepository.save(t);
		
		eventRepository.save(event);

		return t;
		

	}
	
	private Ticket _createTicket(Long orderId, List<ProductRequest> productRequest) {
		Ticket t = new Ticket();
		t.setOrderId(orderId);
		t.setProductRequests(productRequest);
		t.setStatus(TicketStatus.PENDING);
		
		return ticketRepository.save(t);
	}
}
