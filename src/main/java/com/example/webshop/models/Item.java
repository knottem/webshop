package com.example.webshop.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private double price;


    public Item(String name, long price) {
        this.name = name;
        this.price = price;
    }
}
