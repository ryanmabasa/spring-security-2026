package com.example.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * A plain {@link RestClient} aimed at the resource server. The bearer token is
 * attached per-request in {@code HomeController} from the {@code OAuth2AuthorizedClient}
 * that {@code oauth2Login} obtained for the signed-in user.
 */
@Configuration
public class RestClientConfig {

	@Bean
	public RestClient resourceServerRestClient(
			@Value("${app.resource-server.base-url}") String baseUrl) {
		return RestClient.builder()
			.baseUrl(baseUrl)
			.build();
	}

}
