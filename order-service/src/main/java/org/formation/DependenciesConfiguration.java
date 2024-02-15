package org.formation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DependenciesConfiguration {

	@Autowired
	RestTemplateBuilder builder;
		
	@Bean
	@LoadBalanced
	RestTemplate productRestTemplate() {
		return builder.rootUri("http://product").build();
	}
}
