package com.ordermanagementsystem.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Customer {
    private final String customerId;
    private String name;
    private String email;
    private String phone;
    private Address address;

    public Customer(String name, String email, String phone, Address address) {
        this.customerId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
