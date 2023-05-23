package com.example.be.service;

import com.example.be.dto.ICartDetailDto;
import com.example.be.model.CartDetail;

import java.util.List;

public interface ICartDetailService {
    CartDetail save(CartDetail cartDetail);

    List<ICartDetailDto> findAllCartDetail(Integer userId);

    CartDetail findByCartDetailId(Integer cartDetailId);

    void updateAmount(Integer amount, Integer cartDetailId);

    void deleteProduct(Integer userId);

    void deleteCartDetail(Integer cartId, Integer productId);
}
