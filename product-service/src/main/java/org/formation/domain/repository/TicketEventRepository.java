package org.formation.domain.repository;

import org.formation.service.TicketEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketEventRepository extends JpaRepository<TicketEvent, Long> {

}
