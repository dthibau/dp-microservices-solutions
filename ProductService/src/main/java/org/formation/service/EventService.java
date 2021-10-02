package org.formation.service;

import org.formation.domain.event.TicketStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

	private static String TICKET_CHANNEL="ticket-status";
	
	@Autowired
	KafkaTemplate<Long, TicketStatusEvent > kafkaTemplate;
	
	public void notify(TicketStatusEvent ticketEvent) {
		kafkaTemplate.send(TICKET_CHANNEL, ticketEvent);
	}
}
