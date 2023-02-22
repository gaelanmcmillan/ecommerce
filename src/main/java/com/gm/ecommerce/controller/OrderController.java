package com.gm.ecommerce.controller;

import com.gm.ecommerce.assembler.GenericAssembler;
import com.gm.ecommerce.exception.OrderNotFoundException;
import com.gm.ecommerce.model.Order;
import com.gm.ecommerce.model.OrderStatus;
import com.gm.ecommerce.repository.OrderRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {
    private final OrderRepository repository;
    private final GenericAssembler<Order> assembler = new GenericAssembler<Order>(
        order -> {
            EntityModel<Order> entityModel = EntityModel.of(order,
                linkTo(methodOn(CustomerController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).all()).withRel("orders"));

            if (order.getStatus() == OrderStatus.IN_PROGRESS) {
                entityModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
                entityModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
            }

            return entityModel;
        }
    );

    OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders")
    CollectionModel<EntityModel<Order>> all() {
        List<EntityModel<Order>> orders = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    EntityModel<Order> one(@PathVariable Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return assembler.toModel(order);
    }

    @PostMapping("/orders")
    ResponseEntity<?> newOrder(@RequestBody Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        Order newOrder = repository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
                .body(assembler.toModel(newOrder));
    }

    private ResponseEntity<?> changeStatus(Long id, OrderStatus newStatus) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == OrderStatus.IN_PROGRESS) {
            order.setStatus(newStatus);
            return ResponseEntity.ok(assembler.toModel(repository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("An order cannot go from " + order.getStatus() + " to " + newStatus));
    }

    @PutMapping("/orders/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {
        return changeStatus(id, OrderStatus.COMPLETED);
    }

    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {
        return changeStatus(id, OrderStatus.CANCELLED);
    }
}
