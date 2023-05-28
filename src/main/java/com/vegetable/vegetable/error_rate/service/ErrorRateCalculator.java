package com.vegetable.vegetable.error_rate.service;

import com.vegetable.vegetable.predict_product.PredictProduct;
import com.vegetable.vegetable.product.Product;
import java.util.ArrayList;
import java.util.List;


public class ErrorRateCalculator {


// select 기능
    public static List<Double> calc(PredictProduct predictProduct, List<Product> products) {
        // 3. 7일간의 오차율을 구한다.
        List<Double> errorRates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int actualPrice = (products.get(i) != null && products.get(i).getPrice() != 0) ? products.get(i).getPrice() : 1;

            int predictedPrice = 1;
            switch(i) {
                case 0: predictedPrice = predictProduct.getDay1Price(); break;
                case 1: predictedPrice = predictProduct.getDay2Price(); break;
                case 2: predictedPrice = predictProduct.getDay3Price(); break;
                case 3: predictedPrice = predictProduct.getDay4Price(); break;
                case 4: predictedPrice = predictProduct.getDay5Price(); break;
                case 5: predictedPrice = predictProduct.getDay6Price(); break;
                case 6: predictedPrice = predictProduct.getDay7Price(); break;
            }
            double errorRate = Math.abs((predictedPrice - actualPrice) / (double) actualPrice);

            if (errorRate > 100) {
                errorRate = 100;
            }
            errorRates.add(errorRate);
        }
        return errorRates;
    }


}
