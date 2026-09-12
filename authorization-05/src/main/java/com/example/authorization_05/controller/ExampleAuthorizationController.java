package com.example.authorization_05.controller;

import com.example.authorization_05.service.ExampleAuthorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleAuthorizationController {

    private final ExampleAuthorizationService exampleAuthorizationService;

    public ExampleAuthorizationController(ExampleAuthorizationService exampleAuthorizationService) {
        this.exampleAuthorizationService = exampleAuthorizationService;
    }

    @GetMapping("/api/pre-authorized")
    public void preAuthorizeCheck() {
        exampleAuthorizationService.preAuthorizeCheck();
    }

    @GetMapping("/api/post-authorized")
    public void postAuthorizeCheck1() {
        exampleAuthorizationService.postAuthorizeCheck();
    }
}
