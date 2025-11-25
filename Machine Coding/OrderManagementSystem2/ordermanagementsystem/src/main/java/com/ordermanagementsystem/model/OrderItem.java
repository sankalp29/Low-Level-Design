package com.ordermanagementsystem.model;

import lombok.Data;

@Data
public class OrderItem {
    private final String itemId;
    private final Double price;
    private final Integer quantity;

    public OrderItem(Item item, Integer quantity) {
        this.itemId = item.getItemId();
        this.price = item.getPrice();
        this.quantity = quantity;
    }
}
