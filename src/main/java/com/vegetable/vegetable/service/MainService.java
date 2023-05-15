package com.vegetable.vegetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void init() {
//        productService.startService();
        predictProductService.addSampleData();

        errorRateService.createSampleErrorRates();
        otherSiteErrorRateService.createSampleErrorRates();
        productIndexService.createAllIndex();
    }
}
