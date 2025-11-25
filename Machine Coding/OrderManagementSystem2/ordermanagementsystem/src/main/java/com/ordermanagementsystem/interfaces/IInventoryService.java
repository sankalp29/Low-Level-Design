package com.ordermanagementsystem.interfaces;

import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.exceptions.UnknownSellerException;
import com.ordermanagementsystem.model.Item;

public interface IInventoryService {

    void addNewItem(Item item, Integer quantity);

    void addItemToInventory(String itemId, Integer quantity) throws InvalidItemException;

    Integer getAvailableInventory(String itemId, String sellerId) throws InvalidItemException, UnknownSellerException;
}