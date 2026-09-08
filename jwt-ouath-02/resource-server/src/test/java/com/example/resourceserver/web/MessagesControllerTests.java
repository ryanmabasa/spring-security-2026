package com.example.resourceserver.web;

import com.example.resourceserver.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessagesController.class)
@Import(SecurityConfig.class)
class MessagesControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private JwtDecoder jwtDecoder;

	@Test
	@DisplayName("GET /messages without a token is rejected with 401")
	void getWithoutTokenIsUnauthorized() throws Exception {
		mvc.perform(get("/messages"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("GET /messages with scope message.read returns 200")
	void getWithReadScopeIsOk() throws Exception {
		mvc.perform(get("/messages")
				.with(jwt().jwt((jwt) -> jwt.issuer("http://localhost:9000").claim("scope", "message.read"))))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("GET /messages with only message.write scope is forbidden with 403")
	void getWithWrongScopeIsForbidden() throws Exception {
		mvc.perform(get("/messages")
				.with(jwt().jwt((jwt) -> jwt.issuer("http://localhost:9000").claim("scope", "message.write"))))
			.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("POST /messages requires scope message.write")
	void postRequiresWriteScope() throws Exception {
		mvc.perform(post("/messages")
				.contentType("application/json")
				.content("{\"message\":\"hi\"}")
				.with(jwt().jwt((jwt) -> jwt.issuer("http://localhost:9000").claim("scope", "message.read"))))
			.andExpect(status().isForbidden());

		mvc.perform(post("/messages")
				.contentType("application/json")
				.content("{\"message\":\"hi\"}")
				.with(jwt().jwt((jwt) -> jwt.issuer("http://localhost:9000").claim("scope", "message.write"))))
			.andExpect(status().isOk());
	}

}
