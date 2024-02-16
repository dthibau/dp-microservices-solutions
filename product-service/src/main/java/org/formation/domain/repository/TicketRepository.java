package org.formation.domain.repository;

import java.util.Optional;

import org.formation.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

	public Optional<Ticket> findByOrderId(Long orderId);
}
