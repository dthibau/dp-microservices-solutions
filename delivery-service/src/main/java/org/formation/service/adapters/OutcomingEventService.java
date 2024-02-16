package org.formation.service.adapters;

import org.formation.service.LivraisonEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OutcomingEventService {

	@Value("${channels.delivery}")
	String LIVRAISON_CHANNEL;
	
	final KafkaTemplate<Long, LivraisonEvent> kafkaTemplate;
	
	public OutcomingEventService(KafkaTemplate<Long, LivraisonEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void publishEvent(LivraisonEvent livraisonEvent) {
		
		kafkaTemplate.send(LIVRAISON_CHANNEL,livraisonEvent);
	}
}
