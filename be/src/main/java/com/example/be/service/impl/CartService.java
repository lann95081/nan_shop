package com.example.be.service.impl;

import com.example.be.model.Cart;
import com.example.be.repository.ICartRepository;
import com.example.be.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepository iCartRepository;

    @Override
    public Cart save(Cart cart) {
        return iCartRepository.save(cart);
    }
}
