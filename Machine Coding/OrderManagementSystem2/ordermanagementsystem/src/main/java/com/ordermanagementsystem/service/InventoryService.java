package com.ordermanagementsystem.service;

import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.exceptions.UnknownSellerException;
import com.ordermanagementsystem.interfaces.IInventoryService;
import com.ordermanagementsystem.interfaces.IMarketplaceService;
import com.ordermanagementsystem.model.Inventory;
import com.ordermanagementsystem.model.Item;
import com.ordermanagementsystem.model.Seller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryService implements IInventoryService {
    private final IMarketplaceService marketplaceService;

    @Override
    public void addNewItem(Item item, Integer quantity) {
        Seller seller = marketplaceService.getInternalSeller();
        Inventory sellerInventory = seller.getInventory();
        sellerInventory.addNewItem(item, quantity);
    }

    @Override
    public void addItemToInventory(String itemId, Integer quantity) throws InvalidItemException {
        Seller seller = marketplaceService.getInternalSeller();
        Inventory sellerInventory = seller.getInventory();
        sellerInventory.addItemToInventory(itemId, quantity);
    }

    @Override
    public Integer getAvailableInventory(String itemId, String sellerId) throws InvalidItemException, UnknownSellerException {
        Seller seller = marketplaceService.getSellerById(sellerId);
        Inventory inventory = seller.getInventory();
        return inventory.getAvailableQuantity(itemId);
    }
}