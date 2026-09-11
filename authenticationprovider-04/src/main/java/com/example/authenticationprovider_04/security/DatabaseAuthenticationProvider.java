package com.example.authenticationprovider_04.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;


//Just use the DAO authentication provider instead
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private record StoredUser(String encodedPassword, List<String> roles) {
    }

    private final PasswordEncoder passwordEncoder;

    private final Map<String, StoredUser> users;

    public DatabaseAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.users = Map.of(
                "juan", new StoredUser(passwordEncoder.encode("password123"), List.of("ROLE_USER")),
                "admin", new StoredUser(passwordEncoder.encode("adminpass"), List.of("ROLE_USER", "ROLE_ADMIN"))
        );
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();

        StoredUser user = users.get(username);
        if (user == null || credentials == null || !passwordEncoder.matches(credentials.toString(), user.encodedPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        List<SimpleGrantedAuthority> authorities = user.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        //TODO - you usually use an instance of UserDetail as principal
        return UsernamePasswordAuthenticationToken.authenticated(username, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
