package com.ordermanagementsystem.model;

import java.util.List;
import java.util.UUID;

import com.ordermanagementsystem.constants.OrderStatus;
import com.ordermanagementsystem.model.seller.Seller;

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
    private Double totalAmount;

    public Order(String customerId, List<OrderItem> itemsOrdered) {
        this.orderId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemsOrdered = itemsOrdered;
        this.orderStatus = OrderStatus.CREATED;
        this.totalAmount = 0.0;
        for (OrderItem orderItem : itemsOrdered) {
            totalAmount+=(orderItem.getPrice() * orderItem.getQuantity());
        }
    }
}
