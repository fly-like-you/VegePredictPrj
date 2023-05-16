package com.vegetable.vegetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

//      productService.startService();
//      productService.saveProductFromJson(".\\insertData\\product.json");
        List<String> productsNames = productService.getProductsNames();

        otherSiteErrorRateService.createSampleErrorRates();
        productIndexService.createAllIndex();
    }
}
