package org.formation.service;

import java.time.Instant;

import org.formation.domain.Courier;
import org.formation.domain.CourierRepository;
import org.formation.domain.Delivery;
import org.formation.domain.DeliveryRepository;
import org.formation.domain.Status;
import org.formation.service.event.DeliveryEvent;
import org.formation.service.event.TicketStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Log
@Transactional
public class DeliveryService {

	@Value("${app.channel.delivery-event}")
	String DELIVERY_CHANNEL;

	@Autowired
	DeliveryRepository deliveryRepository;

	@Autowired
	CourierRepository courierRepository;

	@Autowired
	KafkaTemplate<Long, DeliveryEvent> kafkaTemplate;

	@KafkaListener(id = "DeliveryService", topics = "${app.channel.ticket-event}")
	public void ticketChanged(TicketStatusEvent ticketEvent) {

		switch (ticketEvent.getStatus()) {

		case "READY_TO_PICK":
			Delivery l = _createDelivery(ticketEvent.getTicketId(), ticketEvent.getOrderId());
			log.info("Livraison créée " + l);
			DeliveryEvent event = new DeliveryEvent(l, "CREE");
			kafkaTemplate.send(DELIVERY_CHANNEL, event);
			break;

		}

	}

	public Delivery affectLivreur(Long livraisonId, Long livreurId) {
		Delivery livraison = deliveryRepository.findById(livraisonId).orElseThrow();
		Courier livreur = courierRepository.findById(livreurId).orElseThrow();
		livraison.setCourier(livreur);
		deliveryRepository.save(livraison);

		DeliveryEvent event = new DeliveryEvent(livraison, "AFFECTE");
		kafkaTemplate.send(DELIVERY_CHANNEL, event);

		return livraison;

	}

	private Delivery _createDelivery(Long ticketId, Long orderId) {
		Delivery l = new Delivery();
		l.setOrderId(orderId);
		l.setCreationDate(Instant.now());
		l.setStatus(Status.CREE);

		return deliveryRepository.save(l);
	}
}
