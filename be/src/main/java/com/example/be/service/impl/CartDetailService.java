package com.example.be.service.impl;

import com.example.be.dto.ICartDetailDto;
import com.example.be.model.CartDetail;
import com.example.be.repository.ICartDetailRepository;
import com.example.be.service.ICartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDetailService implements ICartDetailService {
    @Autowired
    private ICartDetailRepository iCartDetailRepository;


    @Override
    public CartDetail save(CartDetail cartDetail) {
        return iCartDetailRepository.save(cartDetail);
    }

    @Override
    public List<ICartDetailDto> findAllCartDetail(Integer userId) {
        return iCartDetailRepository.findAllCartDetail(userId);
    }

    @Override
    public CartDetail findByCartDetailId(Integer cartDetailId) {
        return iCartDetailRepository.findById(cartDetailId).get();
    }

    @Override
    public void updateAmount(Integer amount, Integer cartDetailId) {
        iCartDetailRepository.updateAmount(amount, cartDetailId);
    }

    @Override
    public void deleteProduct(Integer userId) {
        iCartDetailRepository.deleteProduct(userId);
    }

    @Override
    public void deleteCartDetail(Integer cartId, Integer productId) {
        iCartDetailRepository.deleteCartDetailByCart_CartIdAndProduct_ProductId(cartId, productId);
    }
}
