package com.vegetable.vegetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


@Service
public class MainService {
    private final ProductService productService;
    private final PredictProductService predictProductService;
    private final ErrorRateService errorRateService;

    private final OtherSiteErrorRateService otherSiteErrorRateService;
    private final ProductIndexService productIndexService;

    @Autowired
    public MainService(ProductService productService, PredictProductService predictProductService, ErrorRateService errorRateService, OtherSiteErrorRateService otherSiteErrorRateService, ProductIndexService productIndexService) {
        this.productService = productService;
        this.predictProductService = predictProductService;
        this.errorRateService = errorRateService;
        this.otherSiteErrorRateService = otherSiteErrorRateService;
        this.productIndexService = productIndexService;
    }


    @PostConstruct
    public void init() {
//        // TODO 데이터를 가져오는데 데이터가 없으면 파이썬을 실행시켜서 데이터를 가져와야함!
//        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
//        LocalDate endDate = LocalDate.now(); // 오늘 날짜
//
//        List<LocalDate> dates = new ArrayList<>();
//        while (!startDate.isAfter(endDate)) {
//            dates.add(startDate);
//            startDate = startDate.plusDays(1);
//        }
////        List<String> productsNames = productService.getProductsNames();
////
////        otherSiteErrorRateService.createSampleErrorRates();
//        for (LocalDate date : dates) {
//            productIndexService.saveProductIndex(date);
//        }
    }
}
