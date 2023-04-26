package com.example.webshop.controllers;

import com.example.webshop.models.Item;
import com.example.webshop.repositories.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;}

        @RequestMapping({"/items", "/items/"})
        public List<Item> getAllItems() {return itemRepository.findAll();}

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
