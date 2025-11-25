package com.ordermanagementsystem.model;

import java.util.List;
import java.util.UUID;

import com.ordermanagementsystem.constants.OrderStatus;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="seller")
public class Order {
    private final String orderId;
    private final String customerId;
    private final List<OrderItem> itemsOrdered;
    private Seller seller;
    private OrderStatus orderStatus;

    public Order(String customerId, List<OrderItem> itemsOrdered) {
        this.orderId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemsOrdered = itemsOrdered;
        this.orderStatus = OrderStatus.FREEZED;
    }
}
