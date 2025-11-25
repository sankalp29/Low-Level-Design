package com.ordermanagementsystem.interfaces;

import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.exceptions.InvalidQuantityException;
import com.ordermanagementsystem.exceptions.UnknownSellerException;
import com.ordermanagementsystem.model.Item;

public interface IInventoryService {

    void addNewItem(Item item, Integer quantity) throws InvalidQuantityException;

    void addItemToInventory(String itemId, Integer quantity) throws InvalidItemException, InvalidQuantityException;

    Integer getAvailableInventory(String itemId, String sellerId) throws InvalidItemException, UnknownSellerException;
}