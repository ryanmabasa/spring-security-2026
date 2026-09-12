package com.example.authorization_05.controller;

import com.example.authorization_05.model.Employee;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class EmployeeController {


    @GetMapping("/api/employee")
    @AuthorizeReturnObject
    public Employee getEmployee() {
        return new Employee(1L, BigDecimal.valueOf(1000));
    }
}
