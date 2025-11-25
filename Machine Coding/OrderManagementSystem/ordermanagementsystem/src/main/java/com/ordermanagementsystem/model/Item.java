package com.ordermanagementsystem.model;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of={"itemId"})
public class Item {
    private final String itemId;
    private String name;
    private String brand;
    private String description;
    private Double price;

    public Item(String name, String brand, String description, Double price) {
        this.itemId = UUID.randomUUID().toString();
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
    }
}