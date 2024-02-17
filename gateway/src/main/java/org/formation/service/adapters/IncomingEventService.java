package org.formation.service.adapters;

import org.formation.domain.OrderDto;
import org.formation.domain.repository.OrderDtoRepository;
import org.formation.service.LivraisonEvent;
import org.formation.service.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class IncomingEventService {

	private final OrderDtoRepository orderDtoRepository;

	public IncomingEventService(OrderDtoRepository orderDtoRepository) {
		this.orderDtoRepository = orderDtoRepository;
	}

	@KafkaListener(topics = "#{'${channels.order}'}", id = "gateway")
	public void handleOrderEvent(OrderEvent orderEvent) {

		if (orderEvent.getStatus().equals("APPROVED")) {
			OrderDto orderDto = new OrderDto(orderEvent);
			orderDtoRepository.save(orderDto).block();
		}
	}

	@KafkaListener(topics = "#{'${channels.delivery}'}", id = "query-delivery")
	public void handleDeliveryEvent(LivraisonEvent livraisonEvent) {

		if (livraisonEvent.getStatus().equals("LIVREUR_AFFECTE")) {
			OrderDto orderDto = orderDtoRepository.findByOrderId(livraisonEvent.getLivraison().getOrderId()).block();
			orderDto.setNomLivreur(livraisonEvent.getLivraison().getLivreur().getNom());
			orderDto.setTelephoneLivreur(livraisonEvent.getLivraison().getLivreur().getTelephone());
			
			orderDtoRepository.save(orderDto).block();
			/*Mono<OrderDto> orderDto = orderDtoRepository.findByOrderId(livraisonEvent.getLivraison().getOrderId()).map(o -> {
				o.setNomLivreur(livraisonEvent.getLivraison().getLivreur().getNom());
				o.setNomLivreur(livraisonEvent.getLivraison().getLivreur().getTelephone());
				return o;
			})

			orderDto.map(o -> orderDtoRepository.save(o).then());*/
		}

	}
}
