package com.example.authenticationprovider_04.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Carries a raw API key (pre-authentication) or a resolved username +
 * authorities derived from one (post-authentication). Distinct from
 * {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
 * so {@link ApiKeyAuthenticationProvider} alone claims it via {@code supports()}.
 */
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String apiKey;
    private final @Nullable String username;

    public ApiKeyAuthenticationToken(String apiKey) {
        super((Collection<? extends GrantedAuthority>) null);
        this.apiKey = apiKey;
        this.username = null;
        setAuthenticated(false);
    }

    public ApiKeyAuthenticationToken(String apiKey, String username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        this.username = username;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return username;
    }
}
