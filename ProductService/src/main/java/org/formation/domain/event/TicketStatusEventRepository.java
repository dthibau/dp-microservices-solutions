package org.formation.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketStatusEventRepository extends JpaRepository<TicketStatusEvent, Long>{

}
