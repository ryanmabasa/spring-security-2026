package com.example.authorization_05.controller;

import com.example.authorization_05.model.Employee;
import com.example.authorization_05.security.NameCheck;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class EmployeeController {


    @GetMapping("/api/employee")
    @AuthorizeReturnObject
    //@NameCheck(names = {"user"})
    public Employee getEmployee() {
        return new Employee(1L, BigDecimal.valueOf(1000));
    }
}
