package com.example.webshop.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue()
    private long id;

    private String firstName;

    private String lastName;

    private String socialSecurityNumber;

    public Customer(String firstName, String lastName, String socialSecurityNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
    }
}

