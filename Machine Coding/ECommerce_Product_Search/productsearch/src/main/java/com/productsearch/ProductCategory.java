package com.productsearch;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString (exclude={"categoryId", "createdAt", "updatedAt"})
public class ProductCategory {
    private final String categoryId;
    private String categoryTitle;
    private String categoryDesc;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public ProductCategory(String categoryTitle, String categoryDesc) {
        this.categoryId = UUID.randomUUID().toString();
        this.categoryTitle = categoryTitle;
        this.categoryDesc = categoryDesc;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
        setUpdatedAt();

    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
        setUpdatedAt();
    }

    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
