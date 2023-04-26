package com.example.webshop.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime orderDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    List<Item> items;

    public Order(Customer customer, List<Item> items){
        this.customer = customer;
        this.items = items;
        this.orderDate = LocalDateTime.now();
    }
}
