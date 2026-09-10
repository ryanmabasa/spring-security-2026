package com.example.authentication_03.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
      return "Macho, gwapito lang ako";
    }

    @GetMapping("/super-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String superadmin() {
        return "No way HOSE";
    }
}
