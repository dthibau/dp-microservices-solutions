package org.formation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = GatewayApplication.class)
//@WebFluxTest
@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "org.formation:OrderService:0.0.1-SNAPSHOT:stubs")
public class OrderServiceIntegrationTests {

	
	@Autowired
    private WebTestClient webTestClient;

	String request = "{\n"
			+ "  \"lineItems\": [\n"
			+ "    {\n"
			+ "      \"refProduct\": \"REF1\",\n"
			+ "      \"price\": 10.0,\n"
			+ "      \"quantity\": 2\n"
			+ "    }\n"
			+ "  ],\n"
			+ "  \"deliveryAddress\": {\n"
			+ "    \"rue\": \"RUE\",\n"
			+ "    \"ville\": \"VILLE\",\n"
			+ "    \"codePostal\": \"75001\"\n"
			+ "  },\n"
			+ "  \"paymentInformation\": {\n"
			+ "    \"paymentToken\" : \"AA\"\n"
			+ "  }\n"
			+ "}";

	@Test
	public void routingToOrdrerServiceIsSuccessful() throws Exception {



		String response = webTestClient.get()
        .uri("/actuator/gateway/routes")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .returnResult(String.class)
        .getResponseBody()
        .blockFirst();
		
		System.out.println(response);
		
//		webTestClient.get()
//      .uri("/order/api/orders")
//      .accept(MediaType.APPLICATION_JSON)
//      .exchange()
//      .expectStatus().is2xxSuccessful();
//		
//		webTestClient.post()
//        .uri("/order/api/orders")
//        .body(BodyInserters.fromObject(request))
//        .accept(MediaType.APPLICATION_JSON)
//        .exchange()
//        .expectStatus().is2xxSuccessful();
	}

}
