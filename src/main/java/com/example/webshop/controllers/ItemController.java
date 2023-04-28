package com.example.webshop.controllers;

import com.example.webshop.models.Item;
import com.example.webshop.repositories.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping({"/items", "/items/"})
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @PostMapping({"/items", "/items/"})
    public ResponseEntity<Object> createItem(@RequestBody Item item) {
        if (item == null) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Product not found"), HttpStatus.NOT_FOUND);
        } else if (item.getName().isEmpty() || item.getName() == null || item.getPrice() == 0) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Product is empty"), HttpStatus.BAD_REQUEST);
        } else if (itemRepository.findByName(item.getName()).size() > 0) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Product already exists"), HttpStatus.CONFLICT);
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
            return new ResponseEntity<>(Collections.singletonMap("error", "Product not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
    }

}
