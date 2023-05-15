package com.vegetable.vegetable.service;

import com.vegetable.vegetable.entity.PredictProduct;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.repository.PredictProductRepository;
import com.vegetable.vegetable.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PredictProductService {

    private final PredictProductRepository predictProductRepository;
    private final ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    public PredictProductService(PredictProductRepository predictProductRepository, ProductRepository productRepository) {
        this.predictProductRepository = predictProductRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addSampleData() {
        System.out.println("predictProductService 시작");

        List<PredictProduct> predictProducts = new ArrayList<>();

        // 채소 종류
        String[] vegetableNames = {"깻잎(1kg)", "꽈리고추(1kg)", "시금치(1kg)", "딸기(1kg)", "애호박(20개)",
                "양파(1kg)",
                "쥬키니(1kg)",
                "청양고추(1kg)",
                "파프리카(1kg)",
                "풋고추(1kg)",
        };

        // 2023년 4월 1일부터 2023년 4월 7일까지의 날짜 생성
        LocalDate startDate = LocalDate.now();

        // 가격 랜덤 생성
        Random random = new Random();

        // 샘플 데이터 생성
        for (String vegetableName : vegetableNames) {
            PredictProduct predictProduct = new PredictProduct();
            Optional<Product> productOptional = productRepository.findByNameAndDate(vegetableName, startDate);
            Product product = productOptional.orElseGet(() -> {
                Product newProduct = new Product(vegetableName, 0, startDate);
                entityManager.persist(newProduct); // 영속성 컨텍스트에 저장
                return newProduct;
            });

            predictProduct.setProduct(product);
            for (int i = 1; i <= 7; i++) {
                int price = random.nextInt(10001) + 10000; // 10000원에서 20000원 사이의 가격 랜덤 생성
                switch (i) {
                    case 1:
                        predictProduct.setDay1Price(price);
                        break;
                    case 2:
                        predictProduct.setDay2Price(price);
                        break;
                    case 3:
                        predictProduct.setDay3Price(price);
                        break;
                    case 4:
                        predictProduct.setDay4Price(price);
                        break;
                    case 5:
                        predictProduct.setDay5Price(price);
                        break;
                    case 6:
                        predictProduct.setDay6Price(price);
                        break;
                    case 7:
                        predictProduct.setDay7Price(price);
                        break;
                    default:
                        break;
                }
            }
            predictProducts.add(predictProduct);
        }

        // 샘플 데이터 저장
        predictProductRepository.saveAll(predictProducts);
    }

    public Optional<PredictProduct> getPredictProduct(LocalDate date, String vegetableName) {
        return predictProductRepository.findByProductNameAndProductDate(vegetableName, date);
    }
}
