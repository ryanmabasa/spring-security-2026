package com.example.authorization_05.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    @Profile("local")
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/admin").hasAllAuthorities("read", "write")
                                .requestMatchers("/api/customers").hasAuthority("read")
                )
                .addFilterBefore(new ApiKeyFilter(), AuthorizationFilter.class)
                .build();
    }

    @Bean
    @Profile("example1")
    public SecurityFilterChain apiSecurityFilterChain2(HttpSecurity http) {
        return http
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/admin").access(AuthorizationManagers.allOf(new ReadAuthorizationManager(), AuthorityAuthorizationManager.hasAuthority("write")))
                                .requestMatchers("/api/customers").access(new ReadAuthorizationManager())
                )
                .addFilterBefore(new ApiKeyFilter(), AuthorizationFilter.class)
                .build();
    }

}
