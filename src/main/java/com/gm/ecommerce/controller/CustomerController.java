package com.gm.ecommerce.controller;

import com.gm.ecommerce.model.Customer;
import com.gm.ecommerce.repository.CustomerRepository;
import com.gm.ecommerce.exception.CustomerNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// `@RestController` indicates that the data returned by each method
// will be written straight into the response body
// instead of rendering a template.
@RestController
public class CustomerController {
    private final CustomerRepository repository;

    CustomerController (CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    List<Customer> all () {
        return repository.findAll();
    }

    @PostMapping("/customers")
    Customer newCustomer (@RequestBody Customer newCustomer) {
        return repository.save(newCustomer);
    }

    @GetMapping("/customers/{id}")
    Customer one (@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PutMapping("/customers/{id}")
    Customer replaceCustomer (@RequestBody Customer newCustomer, @PathVariable Long id) {
        return repository.findById(id)
                .map(customer -> {
                    customer.setName(newCustomer.getName());
                    customer.setAddress(newCustomer.getAddress());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                });
    }

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
