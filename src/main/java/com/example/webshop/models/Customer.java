package com.example.webshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "customers")
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    private String firstName;

    private String lastName;

    private String socialSecurityNumber;
}