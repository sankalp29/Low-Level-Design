package com.ordermanagementsystem.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ordermanagementsystem.constants.SellerType;

import lombok.Data;

@Data
public class Marketplace {
    private final Map<String, Seller> externalSellers;
    private final Seller internalSeller;

    public void registerSeller(Seller seller) {
        externalSellers.put(seller.getSellerId(), seller);
    }

    public Marketplace() {
        this.internalSeller = new Seller("FLIPKART", SellerType.INTERNAL);
        this.externalSellers = new ConcurrentHashMap<>();
    }
}
