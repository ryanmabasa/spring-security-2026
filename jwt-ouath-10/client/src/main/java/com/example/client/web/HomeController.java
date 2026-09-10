package com.example.client.web;

import java.util.Map;

import com.example.client.dto.MessagesResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

/**
 * Browser-facing pages for the client app.
 *
 *   GET /           public landing page
 *   GET /messages   protected - shows the current user + the resource server response
 */
@Controller
public class HomeController {

	private final RestClient resourceServerRestClient;

	public HomeController(RestClient resourceServerRestClient) {
		this.resourceServerRestClient = resourceServerRestClient;
	}

	@GetMapping("/")
	public String home(@AuthenticationPrincipal OidcUser user, Model model) {
		model.addAttribute("user", user);
		return "index";
	}

	@GetMapping("/messages")
	public String messages(
			@RegisteredOAuth2AuthorizedClient("my-oidc-client") OAuth2AuthorizedClient authorizedClient,
			Model model) {

		String accessToken = authorizedClient.getAccessToken().getTokenValue();

		// Call the resource server exactly as the diagram shows: Bearer <access token> -> GET /messages
		MessagesResponse response = this.resourceServerRestClient.get()
			.uri("/messages")
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.body(MessagesResponse.class);

		model.addAttribute("scopes", authorizedClient.getAccessToken().getScopes());
		model.addAttribute("tokenValue", accessToken);
		model.addAttribute("response", response);
		return "messages";
	}

}
