package org.formation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;

@DataJpaTest
public class DeliveryRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	DeliveryRepository deliveryRepository;
	
	@Autowired
	ApplicationContext context;
	
	@BeforeEach
	void displayBeans() {
		for ( String beanName : context.getBeanDefinitionNames() ) {
			System.out.println(beanName);
		}
	}
	
	@Test
	void testAffected() {
		
		Delivery delivery1 = entityManager.find(Delivery.class, 1l);
		entityManager.persist(delivery1);

		// Livreu 1 exist
		Courier courier1 = entityManager.find(Courier.class, 1l);
		Delivery delivery2 = Delivery.builder().orderId(2l).creationDate(Instant.now()).courier(courier1).build();
		entityManager.persist(delivery2);

		assertThat(deliveryRepository.findUnaffected()).containsExactly(delivery1);
														
	}
}
