package com.vegetable.vegetable.service;

import com.vegetable.vegetable.entity.PredictProduct;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class ErrorRateServiceTest {

    // ProductService와 PredictProductService를 가상(mock) 객체로 대체하여 테스트
    private ProductService productService = Mockito.mock(ProductService.class);
    private PredictProductService predictProductService = Mockito.mock(PredictProductService.class);
    private ErrorRateService errorRateService = Mockito.mock(ErrorRateService.class);

    @Test
    void calcErrorRate() {

    }
}
