package com.ordermanagementsystem.service;

import java.util.List;
import java.util.Optional;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.constants.SellerType;
import com.ordermanagementsystem.exceptions.UnknownSellerException;
import com.ordermanagementsystem.interfaces.IMarketplaceService;
import com.ordermanagementsystem.model.Inventory;
import com.ordermanagementsystem.model.Item;
import com.ordermanagementsystem.model.Marketplace;
import com.ordermanagementsystem.model.Seller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarketplaceService implements IMarketplaceService {
    private final Marketplace marketplace;

    @Override
    public String registerSeller(String sellerName) {
        Seller seller = new Seller(sellerName, SellerType.EXTERNAL);
        marketplace.registerSeller(seller);
        return seller.getSellerId();
    }

    @Override
    public Seller getInternalSeller() {
        return marketplace.getInternalSeller();
    }

    @Override
    public Seller getSellerById(String sellerId) throws UnknownSellerException {
        if (!marketplace.getExternalSellers().containsKey(sellerId)) 
            throw new UnknownSellerException(ExceptionMessages.UNKNOWN_SELLER_EXCEPTION);
        
        return marketplace.getExternalSellers().get(sellerId);
    }

    @Override
    public Optional<Seller> findSellerAndReserve(List<Item> items) {
        Seller internalSeller = marketplace.getInternalSeller();
        Inventory internalInventory = internalSeller.getInventory();
        if (internalInventory.reserveItems(items)) return Optional.of(internalSeller);

        for (Seller externalSeller : marketplace.getExternalSellers().values()) {
            Inventory inventory = externalSeller.getInventory();
            if (inventory.reserveItems(items)) return Optional.of(externalSeller);
        }

        return Optional.empty();
    }
}