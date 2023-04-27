package com.example.webshop.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    public Customer(String name) {
        this.name = name;
    }
}
