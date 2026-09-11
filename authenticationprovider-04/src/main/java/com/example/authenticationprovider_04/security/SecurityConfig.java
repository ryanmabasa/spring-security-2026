package com.example.authenticationprovider_04.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DatabaseAuthenticationProvider databaseAuthenticationProvider(PasswordEncoder passwordEncoder) {
        return new DatabaseAuthenticationProvider(passwordEncoder);
    }

    @Bean
    ApiKeyAuthenticationProvider apiKeyAuthenticationProvider() {
        return new ApiKeyAuthenticationProvider();
    }

    @Bean
    AuthenticationManager authenticationManager(DatabaseAuthenticationProvider databaseAuthenticationProvider,
                                                 ApiKeyAuthenticationProvider apiKeyAuthenticationProvider) {
        return new ProviderManager(List.of(databaseAuthenticationProvider, apiKeyAuthenticationProvider));
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) {
        http
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .addFilterBefore(new ApiKeyAuthenticationFilter(authenticationManager), AuthorizationFilter.class);

        return http.build();
    }
}
