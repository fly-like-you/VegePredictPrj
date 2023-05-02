package com.vegetable.vegetable.repository;

import com.vegetable.vegetable.entity.ProductIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductIndexRepository extends JpaRepository<ProductIndex, Long> {

}
