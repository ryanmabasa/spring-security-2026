package com.example.resourceserver.web;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.resourceserver.dto.AddMessageResponse;
import com.example.resourceserver.dto.MessageRequest;
import com.example.resourceserver.dto.MessagesResponse;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The protected API from the diagram.
 *
 *   GET  /messages   requires scope "message.read"
 *   POST /messages   requires scope "message.write"
 *
 * Access rules live in {@link com.example.resourceserver.config.SecurityConfig};
 * here we just read the validated {@link Jwt}.
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

	private final List<String> messages = new CopyOnWriteArrayList<>(List.of(
		"Welcome to the resource server",
		"This message required scope message.read"
	));

	@GetMapping
	public MessagesResponse getMessages(@AuthenticationPrincipal Jwt jwt) {
		return new MessagesResponse(
			jwt.getSubject(),
			jwt.getIssuer().toString(),
			jwt.getClaimAsStringList("scope"),
			List.copyOf(messages)
		);
	}

	@PostMapping
	public AddMessageResponse addMessage(@RequestBody MessageRequest request,
			@AuthenticationPrincipal Jwt jwt) {
		messages.add(request.message());
		return new AddMessageResponse(
			jwt.getSubject(),
			Instant.now(),
			List.copyOf(messages)
		);
	}

}
