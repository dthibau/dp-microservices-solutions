package org.formation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DependenciesConfiguration {

	@Autowired
	RestTemplateBuilder builder;
	
	@Bean
	@LoadBalanced
	RestTemplate orderRestTemplate() {
		return builder.rootUri("http://OrderService/api/orders").interceptors(
				new ClientHttpRequestInterceptor(){
			        @Override
			        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			            request.getHeaders().set("Content-Type", "application/json");//Set the header for each request
			            return execution.execute(request, body);
			        }
			    }).build();
	}
	
	@Bean
	@LoadBalanced
	RestTemplate livraisonRestTemplate() {
		return builder.rootUri("http://DeliveryService/api/livraison").interceptors(
				new ClientHttpRequestInterceptor(){
			        @Override
			        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			            request.getHeaders().set("Content-Type", "application/json");//Set the header for each request
			            return execution.execute(request, body);
			        }
			    }).build();
	}
}
