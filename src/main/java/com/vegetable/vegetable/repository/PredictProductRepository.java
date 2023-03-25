package com.vegetable.vegetable.repository;
import com.vegetable.vegetable.entity.PredictProduct;
import com.vegetable.vegetable.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PredictProductRepository extends JpaRepository<PredictProduct, Long> {
    PredictProduct findByProductName(String productName);
    Optional<PredictProduct> findByProductNameAndProductDate(String name, LocalDate date);

}
