package com.ammous.customerservice.controller;

import com.ammous.customerservice.controller.api.CustomerApi;
import com.ammous.customerservice.entities.Customer;
import com.ammous.customerservice.repo.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRestController implements CustomerApi {

    private CustomerRepository customerRepository;


    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public Authentication getAuthentication(Authentication authentication) {
        return authentication;
    }
}
