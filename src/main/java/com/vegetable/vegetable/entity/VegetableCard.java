package com.vegetable.vegetable.entity;

public class VegetableCard {
    private String vegetableName;
    private double pricePercentage;
    private boolean isHigherThanToday;

    public VegetableCard(String vegetableName, double pricePercentage, boolean isHigherThanToday) {
        this.vegetableName = vegetableName;
        this.pricePercentage = pricePercentage;
        this.isHigherThanToday = isHigherThanToday;
    }

    // Getters and setters
    public String getVegetableName() {
        return vegetableName;
    }

    public void setVegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }

    public double getPricePercentage() {
        return pricePercentage;
    }

    public void setPricePercentage(double pricePercentage) {
        this.pricePercentage = pricePercentage;
    }

    public boolean getIsHigherThanToday() {
        return isHigherThanToday;
    }

    public void setIsHigherThanToday(boolean isHigherThanToday) {
        this.isHigherThanToday = isHigherThanToday;
    }
}
