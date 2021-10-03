package org.formation.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.formation.domain.event.TicketStatusEventRepository;
import org.formation.domain.event.TicketStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class MessageRelayService {

	@Autowired
	EventService eventService;
	
	@Autowired
	TicketStatusEventRepository eventRepository;

	@Scheduled(fixedDelay = 10l, timeUnit = TimeUnit.SECONDS)
	public void sendEvents() {
		List<TicketStatusEvent> events = eventRepository.findAll();
		
		events.stream().forEach(e -> {
			log.info("Sending event"+e);
			eventService.notify(e);
		});
		
		eventRepository.deleteAll();
	}
}
