package com.gm.ecommerce;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetCustomersIsList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/customers")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.customerList").hasJsonPath());
	}

	@Test
	void testCreateValidCustomer() throws Exception {
		String requestJson = "{\"name\": \"Sir Buysmore\", \"address\": \"1 Shopalot Place\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sir Buysmore"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.address").value("1 Shopalot Place"));
	}

	@Test
	void testCreateInvalidCustomer() throws Exception {
		String requestJson = "{\"name\": \"\", \"address\": \"1 Shopalot Place\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/customers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
