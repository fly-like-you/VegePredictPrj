package com.vegetable.vegetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private final ProductService productService;
    private final PredictProductService predictProductService;

    @Autowired
    public MainService(ProductService productService, PredictProductService predictProductService) {
        this.productService = productService;
        this.predictProductService = predictProductService;
    }

    public void init() {
        productService.addSampleData();
        predictProductService.addSampleData();
    }
}
