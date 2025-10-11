package com.productsearch.search;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.productsearch.Product;
import com.productsearch.ProductDTO;
import com.productsearch.interfaces.IProductRepository;
import com.productsearch.users.Seller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchService implements ISearch {
    private final IProductRepository productRepository;

    @Override
    public List<ProductDTO> searchByCategory(String productCategoryId) {
        List<ProductDTO> sponsored = new ArrayList<>();
        List<ProductDTO> products = new ArrayList<>();
        List<String> productIds = productRepository.getProductsByCategory(productCategoryId);
        for (String productId : productIds) {
            Product product = productRepository.getProductById(productId);
            ProductDTO productDTO = new ProductDTO(product.getSeller(), product.getBrand(), product.getTitle(), product.getDescription(), product.getCost(), product.getProductCategory());
            if (product.isSponsored()) sponsored.add(productDTO);
            else products.add(productDTO);
        }

        sponsored.addAll(products);
        return sponsored;
    }

    @Override
    public List<ProductDTO> searchByTitle(String text) {
        Predicate<Product> predicate = (product) -> product.getTitle() != null && product.getTitle().equals(text);
        return searchByPredicate(predicate);
    }

    @Override
    public List<ProductDTO> searchBySeller(Seller seller) {
        Predicate<Product> predicate = (product) -> product.getSeller() != null && product.getSeller().equals(seller);
        return searchByPredicate(predicate);
    }

    @Override
    public List<ProductDTO> searchByDesc(String text) {
        Predicate<Product> predicate = (product) -> product.getDescription() != null && product.getDescription().toLowerCase().contains(text.toLowerCase());
        return searchByPredicate(predicate);
    }

    @Override
    public List<ProductDTO> searchByBrand(String text) {
        Predicate<Product> predicate = (product) -> product.getBrand() != null && product.getBrand().toLowerCase().contains(text.toLowerCase());
        return searchByPredicate(predicate);
    }

    private List<ProductDTO> searchByPredicate(Predicate<Product> filter) {
        return productRepository.getAllProducts()
                .stream()
                .filter(filter)
                .sorted(this::compareSponsorship)
                .map(this::getProductDTO)
                .collect(Collectors.toList());
    }

    private Integer compareSponsorship(Product p1, Product p2) {
        return Boolean.compare(p1.isSponsored(), p2.isSponsored());
    }

    private ProductDTO getProductDTO(Product product) {
        return new ProductDTO(product.getSeller(), product.getBrand(), product.getTitle(), product.getDescription(), product.getCost(), product.getProductCategory());
    }
}
