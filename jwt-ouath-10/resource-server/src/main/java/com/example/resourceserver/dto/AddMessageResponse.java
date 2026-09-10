package com.example.resourceserver.dto;

import java.time.Instant;
import java.util.List;

/**
 * Response body for {@code POST /messages}: who added the message, when, and
 * the full list after the addition.
 */
public record AddMessageResponse(
	String addedBy,
	Instant at,
	List<String> messages
) {
}
