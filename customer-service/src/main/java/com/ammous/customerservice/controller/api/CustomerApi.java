package com.ammous.customerservice.controller.api;

import com.ammous.customerservice.entities.Customer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api")
public interface CustomerApi {


    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('USER')")
    public List<Customer> getCustomers();

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id);

    @GetMapping("/mySession")
    public Authentication getAuthentication(Authentication authentication);

}

