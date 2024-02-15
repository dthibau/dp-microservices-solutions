package org.formation.service.adapters;

import org.formation.domain.Livraison;
import org.formation.service.LivraisonService;
import org.formation.service.TicketEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class IncomingEventService {

	private final LivraisonService livraisonService;
	
	public IncomingEventService(LivraisonService livraisonService) {
		this.livraisonService = livraisonService;
	}
	@KafkaListener(id = "delivery", topics = "${channels.ticket}")
	public void ticketChanged(TicketEvent ticketEvent) {
	
			switch (ticketEvent.getNewStatus()) {
	
			case "READY_TO_PICK":
				Livraison l = livraisonService.createDelivery(ticketEvent.getTicket().getTicketId());
				log.info("Livraison créée " + l);
				break;			
			}
	
		}
}
