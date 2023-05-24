package com.example.be.repository;

import com.example.be.dto.ICartDetailDto;
import com.example.be.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ICartDetailRepository extends JpaRepository<CartDetail, Integer> {
    @Query(value = "select c.cart_id as cartId, cd.cart_detail_id as cartDetailId, \n" +
            "p.product_name as productName, p.product_id as productId,\n" +
            "p.price as price, p.img as img, cd.amount as amount, c.user_id as userId \n" +
            "    from product as p \n" +
            "    join cart_detail as cd on cd.product_id = p.product_id \n" +
            "    join cart as c on cd.cart_id = c.cart_id \n" +
            "    where c.user_id = :userId and cd.delete_status = false order by cd.cart_detail_id desc ", nativeQuery = true)
    List<ICartDetailDto> findAllCartDetail(@Param("userId") Integer userId);

    @Modifying
    @Query(value = "update cart_detail set amount = :amount where cart_detail_id = :cartDetailId", nativeQuery = true)
    void updateAmount(@Param("amount") Integer amount,
                      @Param("cartDetailId") Integer cartDetailId);

    @Query(value = "update cart_detail cd \n" +
            "            join cart as c on cd.cart_id = c.cart_id \n" +
            "            set cd.delete_status = true \n" +
            "            where c.user_id = :userId", nativeQuery = true)
    void deleteProduct(@Param("userId") Integer userId);

    @Modifying
    void deleteCartDetailByCartCartIdAndProductProductId(Integer cartId, Integer productId);
}
