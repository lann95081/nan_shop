package com.example.be.controller;

import com.example.be.dto.ICartDetailDto;
import com.example.be.model.*;
import com.example.be.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
public class ProductRestController {
    @Autowired
    private IBrandService iBrandService;

    @Autowired
    private IProductTypeService iProductTypeService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private ICartDetailService iCartDetailService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICartService iCartService;

    @GetMapping("/api/brand")
    public ResponseEntity<List<Brand>> showListBrand() {
        List<Brand> brandList = iBrandService.findAll();
        if (brandList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(brandList, HttpStatus.OK);
        }
    }

    @GetMapping("/api/type")
    public ResponseEntity<List<ProductType>> showListType() {
        List<ProductType> productTypeList = iProductTypeService.findAll();
        if (productTypeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productTypeList, HttpStatus.OK);
        }
    }

    @GetMapping("/api/product")
    public ResponseEntity<List<Product>> showListAndSearch(@RequestParam(defaultValue = "", required = false) String nameSearch,
                                                           @RequestParam(defaultValue = "0", required = false) Integer brandId) {
        List<Product> productList;
        if (brandId == 0) {
            productList = iProductService.findAllByName(nameSearch);
        } else {
            productList = iProductService.findAllByNameAndBrand(nameSearch, brandId);
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/api/product/{productId}")
    public ResponseEntity<Product> findByProductId(@PathVariable Integer productId) {
        Product product = iProductService.findById(productId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @GetMapping("/api/cart/addToCart/{userId}/{productId}/{amount}")
    public ResponseEntity<?> addToCart(@PathVariable Integer userId,
                                       @PathVariable Integer productId,
                                       @PathVariable Integer amount) {

        List<ICartDetailDto> cartDetailDtoList = iCartDetailService.findAllCartDetail(userId);
        for (ICartDetailDto cartDetailDto : cartDetailDtoList) {
            if (Objects.equals(cartDetailDto.getProductId(), productId)) {
                CartDetail cartDetail = iCartDetailService.findByCartDetailId(cartDetailDto.getCartDetailId());
                Integer amount1 = cartDetail.getAmount() + amount;
                cartDetail.setAmount(amount1);
                iCartDetailService.save(cartDetail);
                return new ResponseEntity<>(cartDetail, HttpStatus.OK);
            }
        }

        User user = iUserService.findById(userId);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = dateFormat.format(date);
        Cart cart = new Cart();
        cart.setDate(dateNow);
        cart.setUser(user);
        iCartService.save(cart);
        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart);
        Product product = iProductService.findById(productId);
        cartDetail.setProduct(product);
        cartDetail.setAmount(1);
        CartDetail cartDetail1 = iCartDetailService.save(cartDetail);

        return new ResponseEntity<>(cartDetail1, HttpStatus.CREATED);
    }

    @GetMapping("/api/cart/{userId}")
    public ResponseEntity<List<ICartDetailDto>> findAllCartDetail(@PathVariable Integer userId) {
        List<ICartDetailDto> cartDetailDtoList = iCartDetailService.findAllCartDetail(userId);

        if (cartDetailDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(cartDetailDtoList, HttpStatus.OK);
        }
    }

    @GetMapping("/api/cart/updateAmount/{amount}/{cartDetailId}")
    public ResponseEntity<?> updateAmount(@PathVariable Integer amount, @PathVariable Integer cartDetailId) {
        iCartDetailService.updateAmount(amount, cartDetailId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/deleteProduct/{userId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer userId) {
        iCartDetailService.deleteProduct(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/deleteCartDetail1/{cartId}/{productId}")
    public ResponseEntity<?> deleteCartDetail(@PathVariable Integer cartId, @PathVariable Integer productId) {
        iCartDetailService.deleteCartDetail(cartId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
