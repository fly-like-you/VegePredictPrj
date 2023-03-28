package com.vegetable.vegetable.repository;

import com.vegetable.vegetable.entity.OtherSiteErrorRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherSiteErrorRateRepository  extends JpaRepository<OtherSiteErrorRate, Long> {
}
