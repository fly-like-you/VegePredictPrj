package com.vegetable.vegetable.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "predict_product")
public class PredictProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "is_higher_than_today")
    private boolean isHigherThanToday;

    @ElementCollection
    @CollectionTable(name = "product_predictions")
    @Column(name = "prediction")
    private List<Double> predictions = new ArrayList<>(Collections.nCopies(7, 0.0));

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return this.getProductName();
    }

    public boolean getIsHigherThanToday() {
        return isHigherThanToday;
    }

    public void setIsHigherThanToday(boolean isHigherThanToday) {
        this.isHigherThanToday = isHigherThanToday;
    }
}
