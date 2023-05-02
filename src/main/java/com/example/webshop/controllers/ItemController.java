package com.example.webshop.controllers;

import com.example.webshop.models.BuyOrder;
import com.example.webshop.models.Customer;
import com.example.webshop.models.Item;
import com.example.webshop.models.Order;
import com.example.webshop.repositories.CustomerRepository;
import com.example.webshop.repositories.ItemRepository;
import com.example.webshop.repositories.OrderRepository;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public ItemController(ItemRepository itemRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping({"/items", "/items/"})
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @PostMapping({"/items", "/items/"})
    public ResponseEntity<Object> createItem(@RequestBody Item item) {
        if (item == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Item not found"), HttpStatus.NOT_FOUND);
        } else if (item.getName().isEmpty() || item.getName() == null || item.getPrice() == 0) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Item is empty"), HttpStatus.BAD_REQUEST);
        } else if (itemRepository.findByName(item.getName()).size() > 0) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Item already exists"), HttpStatus.CONFLICT);
        } else {
            itemRepository.save(item);
            logger.info("Item created: " + item.toString());
            return new ResponseEntity<>(item, HttpStatus.CREATED);
        }
    }

    @RequestMapping("/items/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable(required = false) long id) {
        Item item = itemRepository.findById(id).isPresent() ? itemRepository.findById(id).get() : null;
        if (item == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Item not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }

    @PostMapping("/items/buy")
    public ResponseEntity<Object> buyItem(@RequestBody BuyOrder buyOrder) {
        Customer customer = customerRepository.findById(buyOrder.getCustomerId()).isPresent() ? customerRepository.findById(buyOrder.getCustomerId()).get() : null;
        if (customer == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Customer not found"), HttpStatus.NOT_FOUND);
        } else {
            List <Item> itemList = new ArrayList<>();
            List<Long> items = buyOrder.getItemIds();
            for (Long item : items) {
                Item itemFromDb = itemRepository.findById(item).isPresent() ? itemRepository.findById(item).get() : null;
                if (itemFromDb == null) {
                    return new ResponseEntity<>(Collections.singletonMap("error", "Product not found"), HttpStatus.NOT_FOUND);
                } else {
                    itemList.add(itemFromDb);
                }
            }
            Order order = new Order(customer, itemList);
            orderRepository.save(order);
            logger.info("Order created: " + order);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }

    }
}
