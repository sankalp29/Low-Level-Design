package com.productsearch;

import com.productsearch.exceptions.OperationNotAllowedException;
import com.productsearch.exceptions.ProductCategoryException;
import com.productsearch.exceptions.ProductException;
import com.productsearch.interfaces.IProductRepository;
import com.productsearch.interfaces.IProductService;
import com.productsearch.users.Admin;
import com.productsearch.users.Seller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService implements IProductService {
    private IProductRepository productRepository;

    @Override
    public String addProductCategory(Admin admin, String categoryTitle, String categoryDesc) throws ProductCategoryException, OperationNotAllowedException {
        if (admin == null) throw new OperationNotAllowedException("Only a valid admin can add a product category");
        ProductCategory productCategory = new ProductCategory(categoryTitle, categoryDesc);
        productRepository.addProductCategory(productCategory);

        return productCategory.getCategoryId();
    }

    @Override
    public void updateProductCategory(Admin admin, String productCategoryId, String categoryTitle, String categoryDesc) throws ProductCategoryException {
        ProductCategory productCategory = productRepository.getProductCategory(productCategoryId);
        if (productCategory == null) throw new ProductCategoryException("Invalid product category");

        productCategory.setCategoryTitle(categoryTitle);
        productCategory.setCategoryDesc(categoryDesc);
    }

    @Override
    public void deleteProductCategory(Admin admin, String productCategoryId) throws ProductCategoryException {
        ProductCategory productCategory = productRepository.getProductCategory(productCategoryId);
        if (productCategory == null) throw new ProductCategoryException("Invalid product category");
        productRepository.deleteProductCategory(productCategoryId);
    }

    @Override
    public String addProduct(Seller seller, String brand, String title, String description, Double cost, String productCategoryId) throws OperationNotAllowedException, ProductCategoryException, ProductException {
        if (seller == null) throw new OperationNotAllowedException("Only a valid seller can add a product");
        ProductCategory productCategory = productRepository.getProductCategory(productCategoryId);
        if (productCategory == null) throw new ProductCategoryException("Invalid product category id passed in.");
        Product product = new Product(seller, brand, title, description, cost, productCategory);
        productRepository.addProduct(product);

        return product.getProductId();
    }

    @Override
    public void updateProduct(Seller seller, String productId, String title, String desc, Double cost, ProductCategory productCategory) throws OperationNotAllowedException, ProductException {
        if (seller == null) throw new OperationNotAllowedException("Only a valid seller can add a product");
        Product product = productRepository.getProductById(productId);
        if (product == null) throw new ProductException("Product does not exist.");
        if (!product.getSeller().equals(seller)) throw new OperationNotAllowedException("Seller cannot update a product it does not own");

        productRepository.updateProduct(productId, productCategory);
        product.setTitle(title);
        product.setDescription(desc);
        product.setCost(cost);
        product.setProductCategory(productCategory);
    }

    @Override
    public void deleteProduct(Seller seller, String productId) throws ProductException, OperationNotAllowedException {
        if (seller == null) throw new OperationNotAllowedException("Only a valid seller can delete a product");
        Product product = productRepository.getProductById(productId);
        if (product == null) throw new ProductException("Product does not exist.");
        if (!product.getSeller().equals(seller)) throw new OperationNotAllowedException("Seller cannot delete a product it does not own");

        productRepository.deleteProduct(productId);
    }

    @Override
    public void sponsorProduct(Seller seller, String productId) throws OperationNotAllowedException, ProductException {
        if (seller == null) throw new OperationNotAllowedException("Only a valid seller can delete a product");
        Product product = productRepository.getProductById(productId);
        if (product == null) throw new ProductException("Product does not exist.");
        if (!product.getSeller().equals(seller)) throw new OperationNotAllowedException("Seller cannot delete a product it does not own");

        product.sponsor();
    }

    @Override
    public void unsponsorProduct(Seller seller, String productId) throws OperationNotAllowedException, ProductException {
        if (seller == null) throw new OperationNotAllowedException("Only a valid seller can delete a product");
        Product product = productRepository.getProductById(productId);
        if (product == null) throw new ProductException("Product does not exist.");
        if (!product.getSeller().equals(seller)) throw new OperationNotAllowedException("Seller cannot delete a product it does not own");

        product.unsponsor();
    }
}
