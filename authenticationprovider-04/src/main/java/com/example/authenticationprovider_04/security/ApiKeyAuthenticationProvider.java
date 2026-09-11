package com.example.authenticationprovider_04.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;

/**
 * Authenticates a static API key against an in-memory registry, to illustrate
 * a custom {@link AuthenticationProvider} alongside {@link DatabaseAuthenticationProvider}.
 */
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private record ApiKeyPrincipal(String username, List<String> roles) {
    }

    private final Map<String, ApiKeyPrincipal> apiKeys = Map.of(
            "service-key-juan", new ApiKeyPrincipal("juan", List.of("ROLE_USER")),
            "service-key-admin", new ApiKeyPrincipal("admin", List.of("ROLE_USER", "ROLE_ADMIN"))
    );

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = (String) authentication.getCredentials();

        ApiKeyPrincipal principal = apiKeys.get(apiKey);
        if (principal == null) {
            throw new BadCredentialsException("Invalid API key");
        }

        List<SimpleGrantedAuthority> authorities = principal.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new ApiKeyAuthenticationToken(apiKey, principal.username(), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
