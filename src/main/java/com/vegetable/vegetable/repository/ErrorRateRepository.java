package com.vegetable.vegetable.repository;

import com.vegetable.vegetable.entity.ErrorRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRateRepository extends JpaRepository<ErrorRate, Long> {
}
