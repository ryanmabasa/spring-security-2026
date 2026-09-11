package com.example.authorization_05.security;

import com.example.authorization_05.model.Customer;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private final Customer customer;
    public ApiKeyAuthenticationToken(Customer customer) {
        super(customer.getAuthorities());
        this.customer = customer;
        setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return customer.getPassword();
    }

    @Override
    public @Nullable Object getPrincipal() {
        return customer;
    }
}
