package org.formation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OrderQueryServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = {"org.formation:OrderService:0.0.1-SNAPSHOT:stubs","org.formation:DeliveryService:0.0.1-SNAPSHOT:stubs"})
public class QueryServicentegrationTests {

	
	@Autowired
	private MockMvc mockMvc;



	@Test
	public void routingToOrdrerServiceIsSuccessful() throws Exception {



		mockMvc.perform(MockMvcRequestBuilders.get("/query/orders").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

}
