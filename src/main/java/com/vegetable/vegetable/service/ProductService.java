package com.vegetable.vegetable.service;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

        // 당근 데이터
        products.add(new Product("carrot", 1000, LocalDate.of(2022,1,2)));
        products.add(new Product("carrot", 1500, LocalDate.of(2022,1,3)));
        products.add(new Product("carrot", 2000, LocalDate.of(2022,1,4)));

        // 무 데이터
        products.add(new Product("radish", 500, LocalDate.of(2022,1,2)));
        products.add(new Product("radish", 750, LocalDate.of(2022,1,3)));
        products.add(new Product("radish", 1000, LocalDate.of(2022,1,4)));

        // 오이 데이터
        products.add(new Product("cucumber", 3000, LocalDate.of(2022,1,2)));
        products.add(new Product("cucumber", 3500, LocalDate.of(2022,1,3)));
        products.add(new Product("cucumber", 4000, LocalDate.of(2022,1,4)));

        productRepository.saveAll(products);
    }
}