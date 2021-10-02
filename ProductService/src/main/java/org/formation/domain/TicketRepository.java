package org.formation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	public Ticket findByOrderId(Long orderId);
}
