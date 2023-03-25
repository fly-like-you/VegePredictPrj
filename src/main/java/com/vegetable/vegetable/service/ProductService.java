package com.vegetable.vegetable.service;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addSampleData() {
        List<Product> products = new ArrayList<>();

        // 채소 종류
        String[] vegetableNames = {"RedPepper", "Radish", "Cucumber"};

        // 3월 1일부터 3월 31일까지의 날짜 생성
        LocalDate startDate = LocalDate.of(2023, 3, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 31);
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dates.add(date);
        }

        // 가격 랜덤 생성
        Random random = new Random();

        // 샘플 데이터 생성
        for (String vegetableName : vegetableNames) {
            for (LocalDate date : dates) {
                int price = random.nextInt(10001) + 10000; // 10000원에서 20000원 사이의 가격 랜덤 생성
                products.add(new Product(vegetableName, price, date));
            }
        }
        // 샘플 데이터 저장
        productRepository.saveAll(products);
    }

}