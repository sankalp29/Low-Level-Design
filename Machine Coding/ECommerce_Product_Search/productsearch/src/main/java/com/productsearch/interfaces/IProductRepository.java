package com.productsearch.interfaces;

import java.util.List;

import com.productsearch.Product;
import com.productsearch.ProductCategory;
import com.productsearch.exceptions.ProductCategoryException;
import com.productsearch.exceptions.ProductException;

public interface IProductRepository {

    void addProductCategory(ProductCategory productCategory) throws ProductCategoryException;

    ProductCategory getProductCategory(String productCategoryId);

    void deleteProductCategory(String productcategoryId) throws ProductCategoryException;

    List<Product> getAllProducts();

    List<String> getProductsByCategory(String productCategoryId);

    void addProduct(Product product) throws ProductException;

    Product getProductById(String productId);

    void updateProduct(String productId, ProductCategory productCategory);

    void deleteProduct(String productId);

}