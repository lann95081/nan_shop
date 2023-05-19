package com.example.be.service.impl;

import com.example.be.model.Brand;
import com.example.be.repository.IBrandRepository;
import com.example.be.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService implements IBrandService {
    @Autowired
    private IBrandRepository iBrandRepository;

    @Override
    public List<Brand> findAll() {
        return iBrandRepository.findAll();
    }
}
