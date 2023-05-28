package com.vegetable.vegetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;


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
        // TODO 데이터를 가져오는데 데이터가 없으면 파이썬을 실행시켜서 데이터를 가져와야함!


//        productService.startService();
        errorRateService.saveErrorRate(LocalDate.now(), "시금치(1kg)");
//        List<String> productsNames = productService.getProductsNames();
//
//        otherSiteErrorRateService.createSampleErrorRates();
//        productIndexService.saveProductIndex(date);
    }
}
