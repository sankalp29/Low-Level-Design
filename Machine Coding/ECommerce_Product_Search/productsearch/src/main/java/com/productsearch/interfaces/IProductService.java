package com.productsearch.interfaces;

import com.productsearch.ProductCategory;
import com.productsearch.exceptions.OperationNotAllowedException;
import com.productsearch.exceptions.ProductCategoryException;
import com.productsearch.exceptions.ProductException;
import com.productsearch.users.Admin;
import com.productsearch.users.Seller;

public interface IProductService {

    String addProductCategory(Admin admin, String categoryTitle, String categoryDesc) throws ProductCategoryException, OperationNotAllowedException;

    void updateProductCategory(Admin admin, String productCategoryId, String categoryTitle, String categoryDesc) throws ProductCategoryException;

    void deleteProductCategory(Admin admin, String productCategoryId) throws ProductCategoryException;

    public String addProduct(Seller seller, String brand, String title, String description, Double cost, String productCategoryId) throws OperationNotAllowedException, ProductCategoryException, ProductException;

    void updateProduct(Seller seller, String productId, String title, String desc, Double cost, ProductCategory productCategory) throws OperationNotAllowedException, ProductException;

    void deleteProduct(Seller seller, String productId) throws ProductException, OperationNotAllowedException;

    void sponsorProduct(Seller seller, String productId) throws OperationNotAllowedException, ProductException;

    void unsponsorProduct(Seller seller, String productId) throws OperationNotAllowedException, ProductException;

}