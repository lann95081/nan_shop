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
import java.util.Random;

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

    @Autowired
    private IPurchaseService iPurchaseService;

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

        List<ICartDetailDto> cartDetailDtoList = iCartDetailService.findAll(userId);
        for (ICartDetailDto cartDetailDto : cartDetailDtoList) {
            if (Objects.equals(cartDetailDto.getProductId(), productId)) {
                CartDetail cartDetail = iCartDetailService.findByCartDetailId(cartDetailDto.getCartDetailId());
                Integer amount1 = cartDetail.getAmount() + amount;
                if (amount1 > cartDetailDto.getAmountt()) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    cartDetail.setAmount(amount1);
                    iCartDetailService.save(cartDetail);
                    return new ResponseEntity<>(cartDetail, HttpStatus.OK);
                }
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
        cartDetail.setAmount(amount);
        CartDetail cartDetail1 = iCartDetailService.save(cartDetail);
        return new ResponseEntity<>(cartDetail1, HttpStatus.CREATED);
    }

    @GetMapping("/api/cart/{userId}")
    public ResponseEntity<?> findAllCartDetail(@PathVariable Integer userId) {
        List<ICartDetailDto> cartDetailDtoList = iCartDetailService.findAll(userId);

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

    @GetMapping("/api/cart/setCart/{userId}")
    public ResponseEntity<?> setCart(@PathVariable Integer userId) {
        iCartDetailService.setCart(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/deleteCartDetail/{cartId}/{productId}")
    public ResponseEntity<?> deleteCartDetail(@PathVariable Integer cartId, @PathVariable Integer productId) {
        iCartDetailService.deleteCartDetail(cartId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/cart/setAmount/{amount}/{productId}")
    public ResponseEntity<Product> setAmount(@PathVariable Integer amount, @PathVariable Integer productId) {
        iProductService.setAmount(amount, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getAllPurchase(@PathVariable Integer userId) {
        List<PurchaseHistory> purchaseHistoryList = iPurchaseService.findAllByUserId(userId);
        return new ResponseEntity<>(purchaseHistoryList, HttpStatus.OK);
    }

    @GetMapping("save/{userId}/{total}")
    public ResponseEntity<?> saveHistory(@PathVariable Integer userId,
                                         @PathVariable Integer total) {
        List<Integer> cart = iCartDetailService.findAllCartDetail(userId);
        User user = iUserService.findById(userId);
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        Random random = new Random();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.format(date);
        purchaseHistory.setOrderDate(simpleDateFormat.format(date));
        purchaseHistory.setCodeBill(String.valueOf(random.nextInt(90000) + 10000));
        purchaseHistory.setUser(user);
        purchaseHistory.setTotal(Double.valueOf(total));
        iPurchaseService.save(purchaseHistory);
        for (Integer in : cart) {
            CartDetail cartDetail = iCartDetailService.findByCartDetailId(in);
            cartDetail.setPurchaseHistory(purchaseHistory);
            iCartDetailService.save(cartDetail);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
