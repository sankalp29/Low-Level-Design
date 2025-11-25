package com.ordermanagementsystem.model.seller;

import java.util.List;

import com.ordermanagementsystem.constants.SellerType;
import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.model.Inventory;
import com.ordermanagementsystem.model.OrderItem;

import lombok.Getter;

@Getter
public class InternalSeller extends Seller {
    private final Inventory inventory;

    public InternalSeller(String sellerName, SellerType sellerType, Inventory inventory) {
        super(sellerName, sellerType);
        this.inventory = inventory;
    }

    @Override
    public boolean reserveItems(List<OrderItem> orderItems) {
        return inventory.reserveItems(orderItems);
    }

    @Override
    public void releaseItems(List<OrderItem> orderItems) {
        inventory.releaseInventory(orderItems);
    }

    @Override
    public Integer getAvailableQuantity(String itemId) throws InvalidItemException {
        return inventory.getAvailableQuantity(itemId);
    }

}
