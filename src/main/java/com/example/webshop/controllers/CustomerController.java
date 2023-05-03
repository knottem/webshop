package com.example.webshop.controllers;

import com.example.webshop.models.Customer;
import com.example.webshop.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;
    public CustomerController(CustomerRepository repo) {
        customerRepository = repo;
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable long id) {
        Customer customer = customerRepository.findById(id).isPresent() ? customerRepository.findById(id).get() : null;
        if (customer == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Customer not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }

    @PostMapping({"/customers", "/customers/"})
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {
        if (customer == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Customer not found"), HttpStatus.NOT_FOUND);
        } else if (customer.getFirstName() == null ||
                customer.getLastName() == null ||
                customer.getSocialSecurityNumber() == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "null data"), HttpStatus.BAD_REQUEST);
        } else if (customer.getFirstName().isEmpty() ||
                customer.getLastName().isEmpty() ||
                customer.getSocialSecurityNumber().isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Invalid data"), HttpStatus.BAD_REQUEST);
        } else {
            customerRepository.save(customer);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        }
    }
}