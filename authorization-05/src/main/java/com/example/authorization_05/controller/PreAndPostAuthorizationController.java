package com.example.authorization_05.controller;

import com.example.authorization_05.service.PreAndPostAuthorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreAndPostAuthorizationController {

    private final PreAndPostAuthorizationService preAndPostAuthorizationService;

    public PreAndPostAuthorizationController(PreAndPostAuthorizationService preAndPostAuthorizationService) {
        this.preAndPostAuthorizationService = preAndPostAuthorizationService;
    }

    @GetMapping("/api/pre-authorized")
    public void preAuthorizeCheck() {
        preAndPostAuthorizationService.preAuthorizeCheck();
    }

    @GetMapping("/api/post-authorized")
    public void postAuthorizeCheck1() {
        preAndPostAuthorizationService.postAuthorizeCheck();
    }
}
