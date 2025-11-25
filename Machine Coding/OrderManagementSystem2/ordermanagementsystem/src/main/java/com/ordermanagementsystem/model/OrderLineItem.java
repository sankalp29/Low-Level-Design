package com.ordermanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderLineItem {
    private final Item item;
    private final Integer quantity;
}
