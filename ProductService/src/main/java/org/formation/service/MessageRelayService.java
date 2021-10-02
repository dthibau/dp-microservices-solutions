package org.formation.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.formation.domain.ChangeStatusEvent;
import org.formation.domain.ChangeStatusEventRepository;
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
	ChangeStatusEventRepository eventRepository;

	@Scheduled(fixedDelay = 10l, timeUnit = TimeUnit.SECONDS)
	public void sendEvents() {
		List<ChangeStatusEvent> events = eventRepository.findAll();
		
		events.stream().forEach(e -> {
			log.info("Sending event"+e);
			eventService.notify(e);
		});
		
		eventRepository.deleteAll();
	}
}
