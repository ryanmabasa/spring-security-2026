package com.example.authenticationprovider_04.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Reachable either via form login (DatabaseAuthenticationProvider) or an
 * {@code X-Api-Key} header (ApiKeyAuthenticationProvider) — same endpoint, two providers.
 */
@RestController
public class ApiController {

    @GetMapping("/api/me")
    public String me(Authentication authentication) {
        return "Hello, " + authentication.getName() + "! Roles: " + authentication.getAuthorities();
    }
}
