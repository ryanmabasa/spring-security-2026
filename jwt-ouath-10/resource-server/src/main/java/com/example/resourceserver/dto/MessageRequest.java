package com.example.resourceserver.dto;

/**
 * Request body for {@code POST /messages}.
 */
public record MessageRequest(String message) {
}
