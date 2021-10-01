package org.formation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	private static String TICKET_CHANNEL="tickets";
	
	@Autowired
	KafkaTemplate<Long, TicketEvent> kafkaTemplate;
	
	public void notify(TicketEvent ticketEvent) {
		kafkaTemplate.send(TICKET_CHANNEL, ticketEvent);
	}
}
