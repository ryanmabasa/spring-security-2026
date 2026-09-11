package com.example.authorization_05.security;

import com.example.authorization_05.model.Customer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiKeyFilter extends OncePerRequestFilter {


    private final List<Customer> customers = List.of(
            new Customer(1, "user", "user", "123", List.of("read")),
            new Customer(2, "admin", "admin", "123", List.of("read", "write"))
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

       String name = request.getHeader("x-apikey");

       var c = customers.stream().filter(cust -> cust.name().equals(name))
               .findFirst();

       if(c.isEmpty()){
           response.setStatus(HttpStatus.FORBIDDEN.value());
           response.setCharacterEncoding(StandardCharsets.UTF_8.name());
           response.getWriter().write("Siya parin");
           return;
       }
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new ApiKeyAuthenticationToken(c.get()));
        SecurityContextHolder.setContext(securityContext);
        filterChain.doFilter(request, response);



    }
}
