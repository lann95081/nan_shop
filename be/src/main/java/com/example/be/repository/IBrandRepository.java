package com.example.be.repository;

import com.example.be.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBrandRepository extends JpaRepository<Brand, Integer> {
}
