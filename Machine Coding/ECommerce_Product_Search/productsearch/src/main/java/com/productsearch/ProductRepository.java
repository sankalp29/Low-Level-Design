package com.productsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.productsearch.exceptions.ProductCategoryException;
import com.productsearch.exceptions.ProductException;
import com.productsearch.interfaces.IProductRepository;

public class ProductRepository implements IProductRepository {
    private final Map<String, ProductCategory> productCategoryMap;
    private final Map<String, List<String>> productCategoryToProductsMap;
    private final Map<String, Product> productsMap;

    @Override
    public void addProductCategory(ProductCategory productCategory) throws ProductCategoryException {
        if (productCategoryMap.containsKey(productCategory.getCategoryId())) 
            throw new ProductCategoryException("Product category already exists.");
            
        productCategoryMap.put(productCategory.getCategoryId(), productCategory);
    }

    @Override
    public ProductCategory getProductCategory(String productCategoryId) {
        return productCategoryMap.get(productCategoryId);
    }

    @Override
    public void deleteProductCategory(String productcategoryId) throws ProductCategoryException {
        if (!productCategoryMap.containsKey(productcategoryId)) {
            throw new ProductCategoryException("Product category does not exist");
        }
        productCategoryMap.remove(productcategoryId);
        productCategoryToProductsMap.remove(productcategoryId);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(productsMap.values());
    }

    @Override
    public List<String> getProductsByCategory(String productCategoryId) {
        return new ArrayList<>(productCategoryToProductsMap.getOrDefault(productCategoryId, new ArrayList<>()));
    }

    @Override
    public void addProduct(Product product) throws ProductException {
        if (productsMap.containsKey(product.getProductId())) 
            throw new ProductException("Product already exists");

        productsMap.put(product.getProductId(), product);
        productCategoryToProductsMap.computeIfAbsent(product.getProductCategory().getCategoryId(), list -> new ArrayList<>()).add(product.getProductId());
    }

    @Override
    public Product getProductById(String productId) {
        return productsMap.get(productId);
    }

    @Override
    public void updateProduct(String productId, ProductCategory newProductCategory) {
        Product product = getProductById(productId);
        String currentCategoryId = product.getProductCategory().getCategoryId();
        productCategoryToProductsMap.get(currentCategoryId).remove(currentCategoryId);
        productCategoryToProductsMap.computeIfAbsent(newProductCategory.getCategoryId(), list -> new ArrayList<>()).add(productId);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productsMap.get(productId);
        productsMap.remove(productId);
        ProductCategory productCategory = product.getProductCategory();
        String productCategoryId = productCategory.getCategoryId();
        productCategoryToProductsMap.get(productCategoryId).remove(product.getProductId());
    }

    public ProductRepository() {
        productCategoryMap = new ConcurrentHashMap<>();
        productsMap = new ConcurrentHashMap<>();
        productCategoryToProductsMap = new ConcurrentHashMap<>();
    }
}