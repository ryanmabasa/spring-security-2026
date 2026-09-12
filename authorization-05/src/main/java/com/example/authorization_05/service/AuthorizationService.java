package com.example.authorization_05.service;

import com.example.authorization_05.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {


    private static final Logger log = LoggerFactory.getLogger(AuthorizationService.class);

    public boolean check(Customer authenticateCustomer, Customer customerFromDb) {
        log.info("authenticateCustomer: {}, customerFromDb: {}", authenticateCustomer, customerFromDb);
        return true;
    }

    public boolean checkName(Customer customer, String[] names){
        log.info("customer: {}, names: {}", customer, names);
        return true;
    }
}
