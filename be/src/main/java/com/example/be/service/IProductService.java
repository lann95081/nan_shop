package com.example.be.service;

import com.example.be.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAllByName(String nameSearch);

    List<Product> findAllByNameAndBrand(String nameSearch, Integer brandId);

    Product findById(Integer productId);
}
