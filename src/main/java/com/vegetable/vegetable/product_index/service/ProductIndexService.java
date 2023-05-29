package com.vegetable.vegetable.product_index.service;


import com.vegetable.vegetable.product_index.ProductIndex;
import com.vegetable.vegetable.product.Product;
import com.vegetable.vegetable.product_index.ProductIndexRepository;
import com.vegetable.vegetable.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProductIndexService {
    private final ProductIndexRepository productIndexRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductIndexService(ProductIndexRepository producytIndexRepository, ProductRepository productRepository) {
        this.productIndexRepository = producytIndexRepository;
        this.productRepository = productRepository;
    }
    public void createAllIndex() {
        // Product 테이블에서 모든 날짜 가져오기
        List<LocalDate> allDates = productRepository.findAllDates();

        for (LocalDate date : allDates) {
            saveProductIndex(date);
        }
    }

    public void saveProductIndex(LocalDate date) {
        // 해당 날짜로 등록된 농산물들의 가격을 가져옵니다.
        List<Product> products = productRepository.findByDate(date);

        // 해당 날짜에 등록된 농산물이 없을 경우 처리할 로직을 추가할 수 있습니다.
        if (products.isEmpty()) {
            throw new RuntimeException("해당 날짜에 등록된 농산물이 없습니다."); // 예외 발생
        }

        // 가격의 평균값 계산
        double averagePrice = products.stream()
                .mapToInt(Product::getPrice)
                .average()
                .orElse(0.0);

        // 평균 가격과 함께 새로운 Index 객체 생성
        ProductIndex productIndex = new ProductIndex(date, averagePrice);

        // 생성된 Index를 저장
        productIndexRepository.save(productIndex);
    }

    public List<ProductIndex> getAllLastMonthIndexes() {
        LocalDate oneMonthAgo = LocalDate.now().minus(1, ChronoUnit.MONTHS);
        return productIndexRepository.findAllFromLastMonth(oneMonthAgo);
    }
}
