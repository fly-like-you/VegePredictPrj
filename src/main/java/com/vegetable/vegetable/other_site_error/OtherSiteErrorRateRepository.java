package com.vegetable.vegetable.other_site_error;

import com.vegetable.vegetable.other_site_error.OtherSiteErrorRate;
import com.vegetable.vegetable.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtherSiteErrorRateRepository  extends JpaRepository<OtherSiteErrorRate, Long> {
    List<OtherSiteErrorRate> findByName(String name);

    Optional<Product> findByNameAndDate(String name, LocalDate date);
}
