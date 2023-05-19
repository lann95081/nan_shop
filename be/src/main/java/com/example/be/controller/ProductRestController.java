package com.example.be.controller;

import com.example.be.model.Brand;
import com.example.be.model.Product;
import com.example.be.model.ProductType;
import com.example.be.service.IBrandService;
import com.example.be.service.IProductService;
import com.example.be.service.IProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductRestController {
    @Autowired
    private IBrandService iBrandService;

    @Autowired
    private IProductTypeService iProductTypeService;

    @Autowired
    private IProductService iProductService;

    @GetMapping("/list/brand")
    public ResponseEntity<?> showListBrand() {
        List<Brand> brandList = iBrandService.findAll();
        if (brandList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(brandList, HttpStatus.OK);
        }
    }

    @GetMapping("/list/type")
    public ResponseEntity<?> showListType() {
        List<ProductType> productTypeList = iProductTypeService.findAll();
        if (productTypeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productTypeList, HttpStatus.OK);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> showListProduct() {
        List<Product> productList = iProductService.findAll();
        if (productList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productList, HttpStatus.OK);
        }
    }
}
