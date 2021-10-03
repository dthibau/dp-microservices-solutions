package org.formation.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LivraisonRepository extends JpaRepository<Livraison, Long>{

	public Livraison findByOrderId(Long orderId);
	
	@Query("from Livraison l where l.livreur is null")
	public List<Livraison> findUnaffected();
}
