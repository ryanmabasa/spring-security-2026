package com.example.resourceserver.dto;

import java.util.List;

/**
 * Response body for {@code GET /messages}: a few claims from the validated
 * {@link org.springframework.security.oauth2.jwt.Jwt} plus the current messages.
 */
public record MessagesResponse(
	String subject,
	String issuer,
	List<String> scopes,
	List<String> messages
) {
}
