package org.formation;

import java.time.Instant;

import org.formation.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DependenciesConfiguration {

	@Autowired
	RestTemplateBuilder builder;
	
	@Autowired
	TokenService tokenService;
	
	
		
	@Bean
	@LoadBalanced
	RestTemplate productRestTemplate() {
		return builder.rootUri("http://product").build();
	}
	
	@Bean
	@LoadBalanced
	RestTemplate notificationRestTemplate() {
		
		
		RestTemplate rest = builder.rootUri("http://notification").build();
		rest.getInterceptors().add((request, body, execution) -> {
			OAuth2AccessToken accessToken  = tokenService.getToken();
			
			System.out.println(accessToken.getTokenValue());
		    request.getHeaders().setBearerAuth(accessToken.getTokenValue());
		    return execution.execute(request, body);
		});
		
		return rest;
	}
	
}
