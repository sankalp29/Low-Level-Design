package com.ordermanagementsystem.service;

import java.util.List;
import java.util.Optional;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.constants.SellerType;
import com.ordermanagementsystem.exceptions.UnknownSellerException;
import com.ordermanagementsystem.interfaces.IMarketplaceService;
import com.ordermanagementsystem.model.Inventory;
import com.ordermanagementsystem.model.Marketplace;
import com.ordermanagementsystem.model.OrderItem;
import com.ordermanagementsystem.model.seller.InternalSeller;
import com.ordermanagementsystem.model.seller.Seller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarketplaceService implements IMarketplaceService {
    private final Marketplace marketplace;

    @Override
    public String registerSeller(String sellerName) {
        Seller seller = marketplace.registerSeller(sellerName, SellerType.EXTERNAL);
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
    public Optional<Seller> findSellerAndReserve(List<OrderItem> items) {
        InternalSeller internalSeller = (InternalSeller) marketplace.getInternalSeller();
        Inventory internalInventory = internalSeller.getInventory();
        if (internalInventory.reserveItems(items)) return Optional.of(internalSeller);

        for (Seller externalSeller : marketplace.getExternalSellers().values()) {
            if (externalSeller.reserveItems(items)) {
                System.out.println("Reserved from external seller: " + externalSeller);
                return Optional.of(externalSeller);
            }
        }

        return Optional.empty();
    }
}