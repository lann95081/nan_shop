package com.example.be.repository;

import com.example.be.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPurchaseRepository extends JpaRepository<PurchaseHistory, Integer> {
    @Query(value = "select * from purchase_history p where p.user_user_id = :userId order by p.purchase_history_id desc", nativeQuery = true)
    List<PurchaseHistory> findAllByUserId(@Param("userId") Integer userId);
}
