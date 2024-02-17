package org.formation.domain.repository;

import org.formation.domain.OrderDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface OrderDtoRepository extends ReactiveCrudRepository<OrderDto, Long>{

//	@Query(
//            "select order_dto.* from order_dto where order_id = :orderId"
//    )
	Mono<OrderDto> findByOrderId(long orderId);
}
