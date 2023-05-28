package com.vegetable.vegetable.service;

import com.vegetable.vegetable.entity.ErrorRate;
import com.vegetable.vegetable.entity.PredictProduct;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.repository.ErrorRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class ErrorRateService {
    private final ErrorRateRepository errorRateRepository;

    private final ProductService productService;
    private final PredictProductService predictProductService;
    @Autowired
    public ErrorRateService(ErrorRateRepository errorRateRepository, ProductService productService, PredictProductService predictProductService) {
        this.errorRateRepository = errorRateRepository;
        this.productService = productService;
        this.predictProductService = predictProductService;
    }

    public List<ErrorRate> getErrorRatesByName(String name) {
        return errorRateRepository.findByName(name);
    }

    public List<ErrorRate> getAllErrorRates() {
        return errorRateRepository.findAll();
    }


    public void saveErrorRate(LocalDate date, String productName) {
        List<Double> errorRates = calcErrorRate(date, productName);
        System.out.println("errorRates:" + errorRates);
        ErrorRate errorRate = new ErrorRate(date, productName);

        errorRate.setDay1Error(errorRates.get(0));
        errorRate.setDay2Error(errorRates.get(1));
        errorRate.setDay3Error(errorRates.get(2));
        errorRate.setDay4Error(errorRates.get(3));
        errorRate.setDay5Error(errorRates.get(4));
        errorRate.setDay6Error(errorRates.get(5));
        errorRate.setDay7Error(errorRates.get(6));

        errorRateRepository.save(errorRate);
    }

    // 3시에 머신러닝이 완료된 파일이 올라온다. 이때 이를 실행하는 함수가 있어야함
    // 사용자가 요청을 한다고
    // 에러율을 측정 (한 날자를 기준으로 7일간의 예측된 데이터와 실제 데이터의 오차율을 구함)
    private List<Double> calcErrorRate(LocalDate date, String productName) {
        PredictProduct predictProduct;

        // 1. date로부터 7일간의 농산물의 가격이 담긴 product을 불러온다.
        List<Product> products = productService.getProductsForSevenDays(date, productName);

        // 2. date로부터 predictProduct를 불러온다. (predictProduct에는 7일간의 예측가격이 담겨져있다.)
        Optional<PredictProduct> predictProductOptional = predictProductService.getPredictProduct(date, productName);

        // 비어있으면 빈 객체 생성
        if (predictProductOptional.isPresent()) {
            predictProduct = predictProductOptional.get();
        } else{
            return new ArrayList<Double>(Collections.nCopies(7, 100.0));
        }

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
