package com.vegetable.vegetable.repository;

import com.vegetable.vegetable.entity.ErrorRate;
import com.vegetable.vegetable.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ErrorRateRepository extends JpaRepository<ErrorRate, Long> {
    Optional<ErrorRate> findByNameAndDate(String name, LocalDate date);

    List<ErrorRate> findByName(String name);
}
