package com.vegetable.vegetable.product_index;

import com.vegetable.vegetable.product_index.ProductIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductIndexRepository extends JpaRepository<ProductIndex, Long> {

    @Query("SELECT p FROM ProductIndex p WHERE p.date >= :date")
    List<ProductIndex> findAllFromLastMonth(@Param("date") LocalDate date);

}
