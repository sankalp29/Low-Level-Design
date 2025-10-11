package com.productsearch.search;

import java.util.List;

import com.productsearch.ProductDTO;
import com.productsearch.users.Seller;

public interface ISearch {

    List<ProductDTO> searchByCategory(String productCategoryId);

    List<ProductDTO> searchByTitle(String text);

    List<ProductDTO> searchBySeller(Seller seller);

    List<ProductDTO> searchByDesc(String text);

    List<ProductDTO> searchByBrand(String text);
}