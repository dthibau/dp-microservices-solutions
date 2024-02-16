package org.formation.domain.repository;

import java.util.List;
import java.util.Optional;

import org.formation.domain.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LivraisonRepository extends JpaRepository<Livraison, Long>{
	
	Optional<Livraison> findByOrderId(long orderId);

	@Query("from Livraison l where l.livreur is null")
	public List<Livraison> findUnaffected();
	
	@Query("from Livraison l where l.livreur is not null")
	public List<Livraison> findAffected();
}
