package org.formation.service.adapters;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.formation.domain.repository.TicketEventRepository;
import org.formation.service.TicketEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Log
@Transactional
public class EventService {

	@Value("${channels.ticket}")
	public String TICKET_TOPIC;
	
	private KafkaTemplate<Long, TicketEvent> kafkaTemplate;
	private TicketEventRepository ticketEventRepository;
	
	public EventService(KafkaTemplate<Long, TicketEvent> kafkaTemplate, TicketEventRepository ticketEventRepository) {
		this.kafkaTemplate = kafkaTemplate;
		this.ticketEventRepository = ticketEventRepository;
	}

	
	@Scheduled(fixedDelay = 10l, timeUnit = TimeUnit.SECONDS)
	public void sendEvents() {
		List<TicketEvent> events = ticketEventRepository.findAll();
			
			events.stream().forEach(e -> {
				log.info("Sending event"+e);
				CompletableFuture<SendResult<Long,TicketEvent>> future = kafkaTemplate.send(TICKET_TOPIC, e.getTicketId(), e);
				
				try {
					future.get(20,TimeUnit.SECONDS);
					ticketEventRepository.delete(e);
				} catch (Exception ex) {
					log.warning("Unable to send " + e + " : " + ex.getMessage() +" Retrying in 10s");
				}
			});
			
	}		
}
