package com.example.be.service.impl;

import com.example.be.model.PurchaseHistory;
import com.example.be.repository.IPurchaseRepository;
import com.example.be.service.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService implements IPurchaseService {
    @Autowired
    private IPurchaseRepository iPurchaseRepository;

    @Override
    public List<PurchaseHistory> findAllByUserId(Integer userId) {
        return iPurchaseRepository.findAllByUserId(userId);
    }

    @Override
    public PurchaseHistory save(PurchaseHistory purchaseHistory) {
        return iPurchaseRepository.save(purchaseHistory);
    }
}
