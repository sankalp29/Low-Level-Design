package com.ordermanagementsystem.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ordermanagementsystem.constants.SellerType;
import com.ordermanagementsystem.model.seller.Seller;
import com.ordermanagementsystem.model.seller.SellerFactory;

import lombok.Data;

@Data
public class Marketplace {
    private final Map<String, Seller> externalSellers;
    private final Seller internalSeller;

    public Seller registerSeller(String sellerName, SellerType sellerType) {
        Seller seller = SellerFactory.getSeller(sellerName, sellerType);
        externalSellers.put(seller.getSellerId(), seller);
        return seller;
    }

    public Marketplace() {
        this.internalSeller = SellerFactory.getSeller("FLIPKART", SellerType.INTERNAL);
        this.externalSellers = new ConcurrentHashMap<>();
    }
}
