package com.example.be.service;

import com.example.be.model.PurchaseHistory;

import java.util.List;

public interface IPurchaseService {
    List<PurchaseHistory> findAllByUserId(Integer userId);

    PurchaseHistory save (PurchaseHistory purchaseHistory);
}
