package com.example.authorization_05.security;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;


public class ReadAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {


    private static final Logger log = LoggerFactory.getLogger(ReadAuthorizationManager.class);

    @Override
    public @Nullable AuthorizationResult authorize(Supplier<? extends @Nullable Authentication> authentication, RequestAuthorizationContext object) {
        var auth = authentication.get();
        log.info("Checking read authorization for {}", auth);
        var bool = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("read"));
        return bool? new AuthorizationDecision(true) : new AuthorizationDecision(false);
    }
}
