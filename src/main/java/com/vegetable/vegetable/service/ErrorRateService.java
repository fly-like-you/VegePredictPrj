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


    // 기타 필요한 메소드들
    public void createSampleErrorRates() {
        LocalDate startDate = LocalDate.of(2023, 3, 21);
        List<String> productNames = Arrays.asList("깻잎(1kg)", "꽈리고추(1kg)", "시금치(1kg)", "딸기(1kg)", "애호박(20개)",
                "양파(1kg)",
                "쥬키니(1kg)",
                "청양고추(1kg)",
                "파프리카(1kg)",
                "풋고추(1kg)");
        Random random = new Random();

        for (String productName : productNames) {
            for (int i = 0; i < 7; i++) {
                LocalDate date = startDate.plusDays(i);

                ErrorRate errorRate = new ErrorRate();
                errorRate.setDate(date);
                errorRate.setName(productName);

                for (int j = 1; j <= 7; j++) {
                    double error = random.nextDouble() * 10;

                    switch (j) {
                        case 1:
                            errorRate.setDay1Error(error);
                            break;
                        case 2:
                            errorRate.setDay2Error(error);
                            break;
                        case 3:
                            errorRate.setDay3Error(error);
                            break;
                        case 4:
                            errorRate.setDay4Error(error);
                            break;
                        case 5:
                            errorRate.setDay5Error(error);
                            break;
                        case 6:
                            errorRate.setDay6Error(error);
                            break;
                        case 7:
                            errorRate.setDay7Error(error);
                            break;
                    }
                }

                errorRateRepository.save(errorRate);
            }
        }
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

    // 에러율을 측정 (한 날자를 기준으로 7일간의 예측된 데이터와 실제 데이터의 오차율을 구함)
    private List<Double> calcErrorRate(LocalDate date, String productName) {

        // 1. date로부터 7일간의 농산물의 가격이 담긴 product을 불러온다.
        List<Product> products = productService.getProductsForSevenDays(date, productName);

        // 2. date로부터 predictProduct를 불러온다. (predictProduct에는 7일간의 예측가격이 담겨져있다.)
        Optional<PredictProduct> predictProductOptional = predictProductService.getPredictProduct(date, productName);
        PredictProduct predictProduct = predictProductOptional.orElseThrow(NoSuchElementException::new);        // TODO 만약 7일간의 데이터가 없다면? 파이썬으로 실행시켜야함
        System.out.println("predictProduct = " + predictProduct);

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
