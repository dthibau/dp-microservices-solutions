package org.formation.service;

import java.time.Instant;

import org.formation.domain.Livraison;
import org.formation.domain.LivraisonRepository;
import org.formation.domain.Status;
import org.formation.domain.event.TicketStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Log
@Transactional
public class DeliveryService {

	@Autowired
	LivraisonRepository livraisonRepository;

	@KafkaListener(id = "DeliveryService", topics = "#{'${app.channel.ticket-status}'}")
	public void ticketChanged(TicketStatusEvent ticketEvent) {

		switch (ticketEvent.getNewStatus()) {

		case "READY_TO_PICK":
			Livraison l = _createDelivery(ticketEvent.getTicketId(), ticketEvent.getOrderId());
			log.info("Livraison créée " + l);
			break;
			
		}

	}

	private Livraison _createDelivery(Long ticketId, Long orderId) {
		Livraison l = new Livraison();
		l.setCreationDate(Instant.now());
		l.setOrderId(orderId);
		l.setStatus(Status.CREE);

		return livraisonRepository.save(l);
	}
}
