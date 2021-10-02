package org.formation.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("select distinct o from Order o left join fetch o.orderItems where o.id = :id")
	public Optional<Order> fullLoad(@Param("id") Long id);
}
