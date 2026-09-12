package com.example.authorization_05.service;

import com.example.authorization_05.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class ExampleAuthorizationService {


    private static final Logger log = LoggerFactory.getLogger(ExampleAuthorizationService.class);

    //@PreAuthorize("principal.name() == #username")
    @PreAuthorize("principal.name() == 'user'")
    public void preAuthorizeCheck() {
        log.info("name matched");
    }

    @PostAuthorize("@authorizationService.check(principal, returnObject)")
    public Customer postAuthorizeCheck() {
        return new Customer(3, "fromdb", "username","password", List.of());
    }

}
