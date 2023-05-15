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
        // 가상의 데이터 설정
        LocalDate date = LocalDate.now();
        String productName = "Apple";

        // ProductService.getProductsForSevenDays() 메서드가 호출될 때 반환할 가상의 Product 리스트 생성
        List<Product> products = List.of(
                new Product("Apple", 100, date.plusDays(1)),
                new Product("Apple", 105, date.plusDays(2)),
                new Product("Apple", 95, date.plusDays(3)),
                new Product("Apple", 110, date.plusDays(4)),
                new Product("Apple", 100, date.plusDays(5)),
                new Product("Apple", 115, date.plusDays(6)),
                new Product("Apple", 120, date.plusDays(7))
        );
        Mockito.when(productService.getProductsForSevenDays(date, productName))
                .thenReturn(products);

        // PredictProductService.getPredictProduct() 메서드가 호출될 때 반환할 가상의 PredictProduct 객체 생성
        PredictProduct predictProduct = new PredictProduct(null, 105, 110, 100, 115, 95, 120, 130); // 가상의 예측 가격
        Mockito.when(predictProductService.getPredictProduct(date, productName))
                .thenReturn(Optional.of(predictProduct));

        // calcErrorRate 메서드 호출
        List<Double> errorRates = errorRateService.calcErrorRate(date, productName);

        // 예상한 오차율 리스트
        List<Double> expectedErrorRates = List.of(0.05, 0.047619047619047616, 0.05263157894736842,
                0.045454545454545456, 0.15, 0.043478260869565216, 0.08333333333333333);

        // 결과 검증
        Assertions.assertEquals(expectedErrorRates, errorRates);
    }
}
