package org.formation.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{

	public Delivery findByOrderId(Long orderId);
		
	@Query("from Delivery l where l.courier is null")
	public List<Delivery> findUnaffected();
}
