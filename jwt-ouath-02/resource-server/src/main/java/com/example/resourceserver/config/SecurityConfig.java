package com.example.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.oauth2.core.authorization.OAuth2AuthorizationManagers.hasScope;

/**
 * Resource Server configuration, following
 * https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html
 *
 * Every request must carry a Bearer JWT issued by http://localhost:9000
 * (see {@code spring.security.oauth2.resourceserver.jwt.issuer-uri} in application.yml).
 * The JWT signature is verified against the auth server's JWK Set.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(HttpMethod.GET, "/messages").access(hasScope("message.read"))
				.requestMatchers(HttpMethod.POST, "/messages").access(hasScope("message.write"))
				.anyRequest().authenticated()
			)
			.oauth2ResourceServer((oauth2) -> oauth2
				.jwt(Customizer.withDefaults())
			)
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			// a resource server is a stateless API - no browser sessions / forms, so no CSRF
			.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

}
