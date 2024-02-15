package org.formation.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	@Value("${channels.ticket}")
	public String TICKET_TOPIC;
	
	private KafkaTemplate<Long, TicketEvent> kafkaTemplate;
	
	public EventService(KafkaTemplate<Long, TicketEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	public void publish(TicketEvent ticketEvent) {
		kafkaTemplate.send(TICKET_TOPIC, ticketEvent.getTicket().getId(), ticketEvent);
	}
}
