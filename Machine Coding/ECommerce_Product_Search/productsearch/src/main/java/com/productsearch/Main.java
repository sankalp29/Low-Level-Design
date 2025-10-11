package com.productsearch;

import java.util.List;

import com.productsearch.exceptions.OperationNotAllowedException;
import com.productsearch.exceptions.ProductCategoryException;
import com.productsearch.exceptions.ProductException;
import com.productsearch.interfaces.IProductRepository;
import com.productsearch.search.SearchService;
import com.productsearch.users.Account;
import com.productsearch.users.Admin;
import com.productsearch.users.Seller;

public class Main {
    public static void main(String[] args) {
        // testSearchByTitle();
        // testSearchByDesc();
        // testSearchBySeller();
        // testSearchByProductCategoryFailure();
        testSearchByProductCategorySuccess();
    }

    private static void testSearchByTitle() {
        Admin admin = new Admin("Admin", "admin@email.com", new Account());
        Seller seller1 = new Seller("Nike", "help@nike.com", new Account());
        Seller seller2 = new Seller("Adidas", "help@adidas.com", new Account());
        IProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        SearchService searchService = new SearchService(productRepository);
        try {
            String productCategoryId = productService.addProductCategory(admin, "Sports", "Sports gear + equipment");
            productService.addProduct(seller1, "Nike", "Football", "FC-9", 100.0, productCategoryId);
            productService.addProduct(seller2, "Adidas", "Cricket", "ST Bat", 1000.0, productCategoryId);
            
            List<ProductDTO> products = searchService.searchByTitle("football");
            products.stream().forEach(product -> System.out.println(product));
        } catch (ProductCategoryException | OperationNotAllowedException | ProductException e) {
            e.printStackTrace();
        }
    }

    private static void testSearchByDesc() {
        Admin admin = new Admin("Admin", "admin@email.com", new Account());
        Seller seller1 = new Seller("Nike", "help@nike.com", new Account());
        Seller seller2 = new Seller("Adidas", "help@adidas.com", new Account());
        Seller seller3 = new Seller("BDM", "help@bdm.com", new Account());
        IProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        SearchService searchService = new SearchService(productRepository);
        try {
            String productCategoryId = productService.addProductCategory(admin, "Sports", "Sports gear + equipment");
            productService.addProduct(seller1, "Nike", "Football", "FC-9", 100.0, productCategoryId);
            productService.addProduct(seller2, "Adidas","Cricket", "ST Bat", 1000.0, productCategoryId);
            productService.addProduct(seller3, "BDM","Cricket", "BDM Bat", 800.0, productCategoryId);
            List<ProductDTO> products = searchService.searchByDesc("bat");
            products.stream().forEach(product -> System.out.println(product));
        } catch (ProductCategoryException | OperationNotAllowedException | ProductException e) {
            e.printStackTrace();
        }
    }

    private static void testSearchBySeller() {
        Admin admin = new Admin("Admin", "admin@email.com", new Account());
        Seller seller1 = new Seller("Nike", "help@nike.com", new Account());
        Seller seller2 = new Seller("Adidas", "help@adidas.com", new Account());
        Seller seller3 = new Seller("BDM", "help@bdm.com", new Account());
        IProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        SearchService searchService = new SearchService(productRepository);
        try {
            String productCategoryId = productService.addProductCategory(admin, "Sports", "Sports gear + equipment");
            productService.addProduct(seller1, "Nike", "Football", "FC-9", 100.0, productCategoryId);
            productService.addProduct(seller2, "Adidas","Cricket", "ST Bat", 1000.0, productCategoryId);
            productService.addProduct(seller2, "Adidas","Cricket", "ST Premiere Bat", 1200.0, productCategoryId);
            productService.addProduct(seller3, "BDM", "Cricket", "BDM Bat", 800.0, productCategoryId);
            List<ProductDTO> products = searchService.searchBySeller(seller2);
            products.stream().forEach(product -> System.out.println(product));
        } catch (ProductCategoryException | OperationNotAllowedException | ProductException e) {
            e.printStackTrace();
        }
    }

    private static void testSearchByProductCategoryFailure() {
        Admin admin = new Admin("Admin", "admin@email.com", new Account());
        Seller seller1 = new Seller("Nike", "help@nike.com", new Account());
        Seller seller2 = new Seller("Adidas", "help@adidas.com", new Account());
        Seller seller3 = new Seller("BDM", "help@bdm.com", new Account());
        IProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        SearchService searchService = new SearchService(productRepository);
        try {
            String productCategoryId = productService.addProductCategory(admin, "Sports", "Sports gear + equipment");
            productService.addProduct(seller1, "Nike", "Football", "FC-9", 100.0, productCategoryId);
            productService.addProduct(seller2, "Adidas", "Cricket", "ST Bat", 1000.0, productCategoryId);
            productService.addProduct(seller3, "BDM", "Cricket", "BDM Bat", 800.0, productCategoryId);
            List<ProductDTO> products = searchService.searchByCategory("");
            products.stream().forEach(product -> System.out.println(product));
        } catch (ProductCategoryException | OperationNotAllowedException | ProductException e) {
            e.printStackTrace();
        }
    }

    private static void testSearchByProductCategorySuccess() {
        Admin admin = new Admin("Admin", "admin@email.com", new Account());
        Seller seller1 = new Seller("Nike", "help@nike.com", new Account());
        Seller seller2 = new Seller("Adidas", "help@adidas.com", new Account());
        Seller seller3 = new Seller("BDM", "help@bdm.com", new Account());
        IProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        SearchService searchService = new SearchService(productRepository);
        try {
            String productCategory1 = productService.addProductCategory(admin, "Sports", "Sports gear + equipment");
            String productCategory2 = productService.addProductCategory(admin, "Electronics", "All about electronics");
            productService.addProduct(seller1, "Nike", "Football", "FC-9", 100.0, productCategory1);
            productService.addProduct(seller2, "Adidas", "Cricket", "ST Bat", 1000.0, productCategory1);
            productService.addProduct(seller3, "BDM", "Cricket", "BDM Bat", 800.0, productCategory1);

            productService.addProduct(seller3, "Samsung", "TV", "Smart", 2000.0, productCategory2);
            productService.addProduct(seller3, "Apple", "iPhone 17", "Apple's latest launch", 3000.0, productCategory2);
            List<ProductDTO> products = searchService.searchByCategory(productCategory1);
            products.stream().forEach(product -> System.out.println(product));
        } catch (ProductCategoryException | OperationNotAllowedException | ProductException e) {
            e.printStackTrace();
        }
    }
}