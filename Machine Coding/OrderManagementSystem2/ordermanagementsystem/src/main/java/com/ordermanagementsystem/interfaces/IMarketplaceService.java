package com.ordermanagementsystem.interfaces;

import java.util.List;
import java.util.Optional;

import com.ordermanagementsystem.exceptions.UnknownSellerException;
import com.ordermanagementsystem.model.Item;
import com.ordermanagementsystem.model.Marketplace;
import com.ordermanagementsystem.model.Seller;

public interface IMarketplaceService {

    Marketplace getMarketplace();

    String registerSeller(String sellerName);

    Seller getInternalSeller();

    Seller getSellerById(String sellerId) throws UnknownSellerException;

    Optional<Seller> findSellerAndReserve(List<Item> items);
}