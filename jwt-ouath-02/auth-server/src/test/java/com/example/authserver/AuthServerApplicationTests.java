package com.example.authserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthServerApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("The OIDC discovery document is published and advertises the token endpoint")
	void publishesOidcDiscoveryDocument() throws Exception {
		mvc.perform(get("/.well-known/openid-configuration"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.issuer").value("http://localhost"))
			.andExpect(jsonPath("$.token_endpoint").value("http://localhost/oauth2/token"));
	}

	@Test
	@DisplayName("The JWK Set endpoint exposes the public signing key")
	void publishesJwkSet() throws Exception {
		mvc.perform(get("/oauth2/jwks"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.keys[0].kty").value("RSA"));
	}

}
