package org.formation;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class DependenciesConfiguration {

	@Bean
    @LoadBalanced
    WebClient.Builder builder() {
        return WebClient.builder();
    }
    
    @Bean
    WebClient orderWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://order/api/orders").build();
    }

    @Bean
    WebClient livraisonWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://delivery/api/livraisons").build();
    }

    

}
