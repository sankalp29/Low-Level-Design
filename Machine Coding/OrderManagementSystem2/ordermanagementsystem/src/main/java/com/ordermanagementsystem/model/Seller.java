package com.ordermanagementsystem.model;

import java.util.UUID;

import com.ordermanagementsystem.constants.SellerType;

import lombok.Data;

@Data
public class Seller {
    private final String sellerId;
    private final Inventory inventory;
    private String sellerName;
    private SellerType sellerType;

    public Seller(String sellerName, SellerType sellerType) {
        this.sellerName = sellerName;
        this.sellerType = sellerType;
        this.sellerId = UUID.randomUUID().toString();
        this.inventory = new Inventory();
    }
}