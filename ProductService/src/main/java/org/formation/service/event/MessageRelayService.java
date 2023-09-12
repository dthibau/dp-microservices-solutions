package org.formation.service.event;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Log
@Transactional
public class MessageRelayService {

	@Value("${app.channel.ticket-event}")
	private String TICKET_EVENT_CHANNEL;
	
	@Autowired
	KafkaTemplate<Long, TicketStatusEvent> kafkaTemplate;
	
	@Autowired
	TicketStatusEventRepository eventRepository;

	@Scheduled(fixedDelay = 10l, timeUnit = TimeUnit.SECONDS)
	public void sendEvents() {
		List<TicketStatusEvent> events = eventRepository.findAll();
		
		events.stream().forEach(e -> {
			log.info("Sending event"+e);
			kafkaTemplate.send(TICKET_EVENT_CHANNEL, e);
		});
		
		eventRepository.deleteAll();
	}
	

	
	public void notify(TicketStatusEvent ticketEvent) {
		
	}
}
