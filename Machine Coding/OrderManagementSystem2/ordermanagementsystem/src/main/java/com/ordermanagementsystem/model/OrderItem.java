package com.ordermanagementsystem.model;

import lombok.Data;

@Data
public class OrderItem {
    private final String itemId;
    private final Double price;

    public OrderItem(Item item) {
        this.itemId = item.getItemId();
        this.price = item.getPrice();
    }
}
