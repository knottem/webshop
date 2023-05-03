package com.example.webshop.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @OneToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    @ManyToMany
    List<Item> items;

    public Order(Customer customer, List<Item> items){
        this.customer = customer;
        this.items = items;
        this.orderDate = LocalDateTime.now().withNano(0);
    }

    public Order(Customer customer, List<Item> items, LocalDateTime orderDate) {
        this.customer = customer;
        this.items = items;
        this.orderDate = orderDate;
    }
}
