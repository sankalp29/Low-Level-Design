package com.ordermanagementsystem.model.seller;

import java.util.List;

import com.ordermanagementsystem.constants.SellerType;
import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.model.OrderItem;

import lombok.Getter;

@Getter
public class ExternalSeller extends Seller {
    private final ExternalSellerClient externalSellerClient;

    public ExternalSeller(String sellerName, SellerType sellerType, ExternalSellerClient externalSellerClient) {
        super(sellerName, sellerType);
        this.externalSellerClient = externalSellerClient;
    }

    @Override
    public boolean reserveItems(List<OrderItem> orderItems) {
        return externalSellerClient.reserveItems(orderItems);
    }

    @Override
    public void releaseItems(List<OrderItem> orderItems) {
        externalSellerClient.releaseItems(orderItems);
    }

    @Override
    public Integer getAvailableQuantity(String itemId) throws InvalidItemException {
        return externalSellerClient.getAvailableQuantity(itemId);
    }
}
