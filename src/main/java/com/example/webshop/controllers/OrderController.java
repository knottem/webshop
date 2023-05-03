package com.example.webshop.controllers;

import com.example.webshop.models.Order;
import com.example.webshop.repositories.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RequestMapping({ "/orders", "/orders/"})
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @RequestMapping("/orders/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable(required = false) long id) {
        Order order = orderRepository.findById(id).isPresent() ? orderRepository.findById(id).get() : null;
        if (order == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Order not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }
}
