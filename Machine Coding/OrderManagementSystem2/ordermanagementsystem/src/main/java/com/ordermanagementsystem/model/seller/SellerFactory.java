package com.ordermanagementsystem.model.seller;

import com.ordermanagementsystem.constants.SellerType;
import com.ordermanagementsystem.model.Inventory;

public class SellerFactory {
    public static Seller getSeller(String sellerName, SellerType sellerType) {
        switch (sellerType) {
            case INTERNAL:
                return new InternalSeller(sellerName, sellerType, new Inventory());
            case EXTERNAL:
                return new ExternalSeller(sellerName, sellerType, new ExternalSellerClient());
            
            default: throw new IllegalArgumentException();
        }
    }
}
