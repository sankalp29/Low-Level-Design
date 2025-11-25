package com.ordermanagementsystem.model.seller;

import java.util.List;
import java.util.UUID;

import com.ordermanagementsystem.constants.SellerType;
import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.model.OrderItem;

import lombok.Data;

@Data
public abstract class Seller {
    private final String sellerId;
    private String sellerName;
    private SellerType sellerType;

    public Seller(String sellerName, SellerType sellerType) {
        this.sellerId = UUID.randomUUID().toString();
        this.sellerName = sellerName;
        this.sellerType = sellerType;
    }

    public abstract boolean reserveItems(List<OrderItem> orderItems);
    public abstract void releaseItems(List<OrderItem> orderItems);
    public abstract Integer getAvailableQuantity(String itemId) throws InvalidItemException;
}