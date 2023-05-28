package com.vegetable.vegetable.product_index;

import com.vegetable.vegetable.product_index.ProductIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductIndexRepository extends JpaRepository<ProductIndex, Long> {

}
