package org.formation.service;

import java.time.Instant;

import org.formation.domain.Livraison;
import org.formation.domain.LivraisonRepository;
import org.formation.domain.Livreur;
import org.formation.domain.LivreurRepository;
import org.formation.domain.Status;
import org.formation.domain.event.DeliveryEvent;
import org.formation.domain.event.TicketStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Log
@Transactional
public class DeliveryService {

	@Value("${app.channel.delivery-status}")
	String DELIVERY_CHANNEL;
	
	@Autowired
	LivraisonRepository livraisonRepository;
	
	@Autowired
	LivreurRepository livreurRepository;
	
	@Autowired
	KafkaTemplate<Long, DeliveryEvent> kafkaTemplate;
	

	@KafkaListener(id = "DeliveryService", topics = "#{'${app.channel.ticket-status}'}")
	public void ticketChanged(TicketStatusEvent ticketEvent) {

		switch (ticketEvent.getNewStatus()) {

		case "READY_TO_PICK":
			Livraison l = _createDelivery(ticketEvent.getTicketId(), ticketEvent.getOrderId());
			log.info("Livraison créée " + l);
			DeliveryEvent event = new DeliveryEvent(l, "CREE");
			kafkaTemplate.send(DELIVERY_CHANNEL,event);
			break;
			
		}

	}

	public Livraison affectLivreur(Long livraisonId, Long livreurId) {
		Livraison livraison = livraisonRepository.findById(livraisonId).orElseThrow();
		Livreur livreur = livreurRepository.findById(livreurId).orElseThrow();
		livraison.setLivreur(livreur);
		livraisonRepository.save(livraison);
		
		DeliveryEvent event = new DeliveryEvent(livraison,"AFFECTE");
		kafkaTemplate.send(DELIVERY_CHANNEL,event);
		
		return livraison;
		
	}
	
	private Livraison _createDelivery(Long ticketId, Long orderId) {
		Livraison l = new Livraison();
		l.setCreationDate(Instant.now());
		l.setOrderId(orderId);
		l.setStatus(Status.CREE);

		return livraisonRepository.save(l);
	}
}
