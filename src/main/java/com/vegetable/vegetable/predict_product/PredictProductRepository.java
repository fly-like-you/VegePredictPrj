package com.vegetable.vegetable.predict_product;
import com.vegetable.vegetable.predict_product.PredictProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PredictProductRepository extends JpaRepository<PredictProduct, Long> {
    PredictProduct findByName(String name);
    Optional<PredictProduct> findByNameAndDate(String name, LocalDate date);


}
