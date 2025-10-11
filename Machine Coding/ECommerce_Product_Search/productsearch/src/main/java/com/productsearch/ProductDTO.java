package com.productsearch;

import com.productsearch.users.Seller;

import lombok.ToString;

@ToString
public class ProductDTO {
    private final Seller seller;
    private final String brand;
    private final String title;
    private final String description;
    private final Double cost;
    private final ProductCategory productCategory;

    public ProductDTO(Seller seller, String brand, String title, String description, Double cost, ProductCategory productCategory) {
        this.seller = seller;
        this.brand = brand;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.productCategory = productCategory;
    }
}
