package com.example.filter_demo_02;

import com.example.filter_demo_02.controller.ApiController;
import com.example.filter_demo_02.controller.PublicController;
import com.example.filter_demo_02.security.FilterdemoSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FilterdemoSecurityConfig.class, ApiController.class, PublicController.class})
@WebAppConfiguration
class FilterdemoApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	@DisplayName("The /hello endpoint called publicly")
	public void testPublic() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(unauthenticated())
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Filter demo filter called")
	public void testApi() throws Exception {
		mockMvc.perform(get("/api/test"))
				.andExpect(unauthenticated())
				.andExpect(content().string("Hee hee"));
	}

}
