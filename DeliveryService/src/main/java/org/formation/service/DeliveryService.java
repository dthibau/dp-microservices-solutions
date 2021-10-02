package org.formation.service;

import java.time.Instant;

import org.formation.domain.ChangeStatusEvent;
import org.formation.domain.Delivery;
import org.formation.domain.DeliveryRepository;
import org.formation.domain.Status;
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
	DeliveryRepository deliveryRepository;

	@KafkaListener(id = "DeliveryService", topics = "${channels.ticket-channel}")
	public void ticketChanged(ChangeStatusEvent ticketEvent) {

		switch (ticketEvent.getNewStatus()) {

		case "READY_TO_PICK":
			Delivery l = _createDelivery(ticketEvent.getTicketId());
			log.info("Livraison créée " + l);
			break;
			
		}

	}

	private Delivery _createDelivery(Long ticketId) {
		Delivery l = new Delivery();
		l.setCreationDate(Instant.now());
		l.setNoCommande("" + ticketId);
		l.setStatus(Status.CREE);

		return deliveryRepository.save(l);
	}
}
