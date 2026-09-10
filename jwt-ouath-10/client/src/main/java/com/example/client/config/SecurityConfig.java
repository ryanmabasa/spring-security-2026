package com.example.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * OAuth2 Client configuration, following
 * https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html
 *
 * {@code oauth2Login()} turns this app into an OpenID Connect Relying Party:
 * hitting a protected page redirects the browser to the auth server
 * (http://localhost:9000), runs the authorization_code flow, and drops an
 * authenticated session cookie. The obtained access token is then reused to
 * call the resource server (see {@code HomeController#messages}).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.oauth2Login(Customizer.withDefaults())
				.oauth2Client(Customizer.withDefaults());

		return http.build();
	}

}
