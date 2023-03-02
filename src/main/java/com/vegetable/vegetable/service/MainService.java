package com.vegetable.vegetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    private final ProductService productService;

    @Autowired
    public MainService(ProductService productService) {
        this.productService = productService;
    }

    public void init() {
        productService.addSampleData();
    }
}
