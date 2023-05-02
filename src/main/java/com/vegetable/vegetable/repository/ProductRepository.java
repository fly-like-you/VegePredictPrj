package com.vegetable.vegetable.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.vegetable.vegetable.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameAndDate(String name, LocalDate date);

    List<Product> findByName(String name);

    List<Product> findByDate(LocalDate date);

    @Query("SELECT DISTINCT p.name FROM Product p")
    List<String> findAllDistinctNames();

    @Query("SELECT DISTINCT p.date FROM Product p")
    List<LocalDate> findAllDates();
}
