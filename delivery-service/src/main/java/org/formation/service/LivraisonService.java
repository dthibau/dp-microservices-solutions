package org.formation.service;

import java.time.Instant;

import org.formation.domain.Livraison;
import org.formation.domain.LivraisonRepository;
import org.formation.domain.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LivraisonService {

	private final LivraisonRepository livraisonRepository;

	public LivraisonService(LivraisonRepository livraisonRepository) {
		this.livraisonRepository = livraisonRepository;
	}

	public Livraison createDelivery(Long ticketId) {
		Livraison l = new Livraison();
		l.setCreationDate(Instant.now());
		l.setNoCommande("" + ticketId);
		l.setStatus(Status.CREE);

		return livraisonRepository.save(l);
	}

}
