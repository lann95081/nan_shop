package com.example.be.service.impl;

import com.example.be.model.Product;
import com.example.be.repository.IProductRepository;
import com.example.be.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public List<Product> findAllByName(String nameSearch) {
        return iProductRepository.findAllByProductName(nameSearch);
    }

    @Override
    public List<Product> findAllByNameAndBrand(String nameSearch, Integer brandId) {
        return iProductRepository.findAllByProductNameAndBrandId(nameSearch, brandId);
    }

    @Override
    public Product findById(Integer productId) {
        return iProductRepository.findById(productId).get();
    }

    @Override
    public void setAmount(Integer amount, Integer productId) {
        iProductRepository.setAmount(amount, productId);
    }
}
