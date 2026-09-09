package com.example.client.dto;

import java.util.List;

public record MessagesResponse(
        String subject,
        String issuer,
        List<String> scopes,
        List<String> messages
) {
}
