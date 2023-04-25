package com.example.webshop.models;

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

    private LocalDateTime orderDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    List<Order> orders;

    public Order(Customer customer, List<Order> orders){
        this.customer = customer;
        this.orders = orders;
        this.orderDate = LocalDateTime.now();
    }
}
