package com.ordermanagementsystem.model.seller;

import java.util.List;

import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.model.OrderItem;

public class ExternalSellerClient {
    public boolean reserveItems(List<OrderItem> orderItems) {
        // Call Seller API and return result
        return true;
    }

    public void releaseItems(List<OrderItem> orderItems) {
        // Call Seller API and return result
    }

    public Integer getAvailableQuantity(String itemId) throws InvalidItemException {
        // Call Seller API and return result
        return 0;
    }
}
