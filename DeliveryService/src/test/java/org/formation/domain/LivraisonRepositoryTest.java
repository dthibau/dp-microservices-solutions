package org.formation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;

@DataJpaTest
public class LivraisonRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	LivraisonRepository livraisonRepository;
	
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
		
		// Livarison existe
		Livraison livraison1 = entityManager.find(Livraison.class, 1l);
		// Livreu 1 exist
		Livreur livreur1 = entityManager.find(Livreur.class, 1l);
		Livraison livraison2 = Livraison.builder().orderId(2l).creationDate(Instant.now()).livreur(livreur1).build();
		entityManager.persist(livraison2);

		assertThat(livraisonRepository.findUnaffected()).containsExactly(livraison1);
														
	}
}
