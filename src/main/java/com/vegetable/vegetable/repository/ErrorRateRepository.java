package com.vegetable.vegetable.repository;

import com.vegetable.vegetable.entity.ErrorRate;
import com.vegetable.vegetable.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorRateRepository extends JpaRepository<ErrorRate, Long> {
    List<ErrorRate> findByName(String name);
}
