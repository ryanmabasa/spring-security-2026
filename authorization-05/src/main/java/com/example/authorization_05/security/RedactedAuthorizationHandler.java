package com.example.authorization_05.security;

import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.method.MethodAuthorizationDeniedHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class RedactedAuthorizationHandler implements MethodAuthorizationDeniedHandler {
    @Override
    public @Nullable Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {
        return BigDecimal.ZERO;
    }
}
