package com.example.authentication_03.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    public CustomAuthenticationToken() {
        super(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return "admin";
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
