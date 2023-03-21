package com.vegetable.vegetable.repository;
import com.vegetable.vegetable.entity.PredictProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictProductRepository extends JpaRepository<PredictProduct, Long> {
    PredictProduct findByProductName(String productName);
}
