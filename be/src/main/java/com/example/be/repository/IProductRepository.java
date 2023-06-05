package com.example.be.repository;

import com.example.be.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface IProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "select * from product where product_name like concat('%',:nameSearch,'%') order by product.product_id desc", nativeQuery = true)
    List<Product> findAllByProductName(@Param("nameSearch") String nameSearch);

    @Query(value = "select * from product where product_name like concat('%',:nameSearch,'%') and brand_id = :brandId order by product.product_id desc", nativeQuery = true)
    List<Product> findAllByProductNameAndBrandId(@Param("nameSearch") String nameSearch,
                                                 @Param("brandId") Integer brandId);

    @Modifying
    @Query(value = "update product set amount = :amount where product_id = :productId",nativeQuery = true)
    void setAmount(@Param("amount") Integer amount,
                   @Param("productId") Integer productId);
}
