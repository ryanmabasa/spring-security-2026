package com.example.authorization_05.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @GetMapping("/api/customers")
    public String test(Authentication authentication) {
        return String.valueOf(authentication.getAuthorities());
    }
}
