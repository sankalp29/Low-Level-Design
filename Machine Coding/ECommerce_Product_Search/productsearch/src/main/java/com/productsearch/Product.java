package com.productsearch;

import java.time.LocalDateTime;
import java.util.UUID;

import com.productsearch.exceptions.ProductCategoryException;
import com.productsearch.users.Seller;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude={"createdAt", "updatedAt"})
public class Product {
    private final String productId;
    private final Seller seller;
    private final String brand;
    private String title;
    private String description;
    private Double cost;
    private ProductCategory productCategory;
    private boolean sponsored;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(Seller seller, String brand, String title, String description, Double cost, ProductCategory productCategory) throws ProductCategoryException {
        if (productCategory == null) throw new ProductCategoryException("You must speficy a product category.");
        this.productId = UUID.randomUUID().toString();
        this.seller = seller;
        this.brand = brand;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.productCategory = productCategory;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public synchronized void setTitle(String title) {
        this.title = title;
        setUpdatedAt();
    }

    public synchronized void setDescription(String description) {
        this.description = description;
        setUpdatedAt();
    }

    public synchronized void setCost(Double cost) {
        this.cost = cost;
        setUpdatedAt();
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        setUpdatedAt();
    }

    public synchronized void sponsor() {
        this.sponsored = true;
    }

    public synchronized void unsponsor() {
        this.sponsored = false;
    }

    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
