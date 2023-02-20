package com.gm.ecommerce.controller;

import com.gm.ecommerce.assembler.GenericAssembler;
import com.gm.ecommerce.model.Customer;
import com.gm.ecommerce.repository.CustomerRepository;
import com.gm.ecommerce.exception.CustomerNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// `@RestController` indicates that the data returned by each method
// will be written straight into the response body
// instead of rendering a template.
@RestController
public class CustomerController {
    private final CustomerRepository repository;
    private final GenericAssembler<Customer> assembler = new GenericAssembler<Customer>(
            customer -> EntityModel.of(customer,
                    linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
                    linkTo(methodOn(CustomerController.class).all()).withRel("customers"))
    );

    CustomerController (CustomerRepository repository) {
        this.repository = repository;
    }

    // Below is an ASCIIDoc tag.
    // Read more here: https://docs.asciidoctor.org/asciidoc/latest/directives/include-tagged-regions/

    // Aggregate Root

    // tag::get-aggregate-root[]
    @GetMapping("/customers")
    CollectionModel<EntityModel<Customer>> all () {
        List<EntityModel<Customer>> customers = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/customers")
    Customer newCustomer (@RequestBody Customer newCustomer) {
        return repository.save(newCustomer);
    }

    @GetMapping("/customers/{id}")
    EntityModel<Customer> one (@PathVariable Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return assembler.toModel(customer);
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
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
