package com.example.authorization_05.model;

import com.example.authorization_05.security.RedactedAuthorizationHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;

import java.math.BigDecimal;

public class Employee {

    private Long id;
    private BigDecimal salary;

    public Employee(Long id, BigDecimal salary) {
        this.id = id;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    @PreAuthorize("hasAllAuthorities('read','write')")
    @HandleAuthorizationDenied(
            handlerClass = RedactedAuthorizationHandler.class
    )
    public BigDecimal getSalary() {
        return salary;
    }
}
