package com.vegetable.vegetable.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VegetableCard {
    private String vegetableName;
    private double pricePercentage;
    private boolean isHigherThanToday;
    private int price;

    public VegetableCard(String vegetableName, double pricePercentage, boolean isHigherThanToday, int price) {
        this.vegetableName = vegetableName;
        this.pricePercentage = pricePercentage;
        this.isHigherThanToday = isHigherThanToday;
        this.price = price;
    }

}
