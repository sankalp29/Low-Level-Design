package com.ordermanagementsystem.service;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.exceptions.InvalidQuantityException;
import com.ordermanagementsystem.exceptions.UnknownSellerException;
import com.ordermanagementsystem.interfaces.IInventoryService;
import com.ordermanagementsystem.interfaces.IMarketplaceService;
import com.ordermanagementsystem.model.Inventory;
import com.ordermanagementsystem.model.Item;
import com.ordermanagementsystem.model.seller.InternalSeller;
import com.ordermanagementsystem.model.seller.Seller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryService implements IInventoryService {
    private final IMarketplaceService marketplaceService;

    @Override
    public void addNewItem(Item item, Integer quantity) throws InvalidQuantityException {
        if (quantity <= 0) throw new InvalidQuantityException(ExceptionMessages.INVALID_QUANTITY_EXCEPTION);
        Seller seller = marketplaceService.getInternalSeller();
        InternalSeller internalSeller = (InternalSeller) seller;
        Inventory sellerInventory = internalSeller.getInventory();
        sellerInventory.addNewItem(item, quantity);
    }

    @Override
    public void addItemToInventory(String itemId, Integer quantity) throws InvalidItemException, InvalidQuantityException {
        if (quantity <= 0) throw new InvalidQuantityException(ExceptionMessages.INVALID_QUANTITY_EXCEPTION);
        Seller seller = marketplaceService.getInternalSeller();
        InternalSeller internalSeller = (InternalSeller) seller;
        Inventory sellerInventory = internalSeller.getInventory();
        sellerInventory.addItemToInventory(itemId, quantity);
    }

    @Override
    public Integer getAvailableInventory(String itemId, String sellerId) throws InvalidItemException, UnknownSellerException {
        Seller seller;
        if (marketplaceService.getInternalSeller().getSellerId().equals(sellerId)) {
            seller = marketplaceService.getInternalSeller();
        } else {
            seller = marketplaceService.getSellerById(sellerId);
        }
        return seller.getAvailableQuantity(itemId);
    }
}