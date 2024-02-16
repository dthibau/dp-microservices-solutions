package org.formation.service;

import java.time.Instant;

import org.formation.domain.Livraison;
import org.formation.domain.Livreur;
import org.formation.domain.Status;
import org.formation.domain.repository.LivraisonRepository;
import org.formation.service.adapters.OutcomingEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LivraisonService {

	private final LivraisonRepository livraisonRepository;
	private final OutcomingEventService outcomingEventService;

	public LivraisonService(LivraisonRepository livraisonRepository, OutcomingEventService outcomingEventService) {
		this.livraisonRepository = livraisonRepository;
		this.outcomingEventService = outcomingEventService;
	}

	public Livraison createDelivery(Long orderId, Long ticketId) {
		Livraison l = new Livraison();
		l.setCreationDate(Instant.now());
		l.setOrderId(orderId);
		l.setTicketId(ticketId);
		l.setStatus(Status.CREE);
		Livraison ret = livraisonRepository.save(l);

		outcomingEventService.publishEvent(new LivraisonEvent(ret, Status.CREE.toString()));

		return ret;

	}

	public Livraison affect(Livraison livraison, Livreur livreur) {
		livraison.setLivreur(livreur);
		livraison.setStatus(Status.LIVREUR_AFFECTE);
		Livraison ret = livraisonRepository.save(livraison);

		outcomingEventService.publishEvent(new LivraisonEvent(ret, Status.LIVREUR_AFFECTE.toString()));

		return ret;
	}

}
