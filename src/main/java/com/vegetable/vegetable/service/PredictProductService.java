package com.vegetable.vegetable.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vegetable.vegetable.entity.PredictProduct;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.repository.PredictProductRepository;
import com.vegetable.vegetable.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PredictProductService {

    private final PredictProductRepository predictProductRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PredictProductService(PredictProductRepository predictProductRepository, ProductRepository productRepository) {
        this.predictProductRepository = predictProductRepository;
        this.productRepository = productRepository;
    }

    public Optional<PredictProduct> getPredictProduct(LocalDate date, String vegetableName) {
        return predictProductRepository.findByProductNameAndProductDate(vegetableName, date);
    }

    @Async
    @Scheduled(cron = "0 30 11 * * ?") // Every day at 11:30
    public void runPythonDeepLearning() {
        String pythonPath = "C:/Users/Parkjunho/anaconda3/envs/MachineLearning/python.exe";
        try {
            // 파이썬 스크립트 실행
            ProcessBuilder pb = new ProcessBuilder(pythonPath, "./predict_python/price_predict/MachineLearnng.py");
            pb.inheritIO();
            pb.start();

        } catch (IOException e) {
            System.out.println("Error during script execution: " + e.getMessage());
            e.printStackTrace();
        }

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);

        String filePath = "./predict_python/data/predict/predict_from_" + formattedDate +".json";
        savePredictionFromJson(filePath);
    }

    public void savePredictionFromJson(String filePath) {
        System.out.println("PredictProductService.savePredictionFromJson 실행");
        ObjectMapper mapper = new ObjectMapper();

        try {
            // JSON 파일을 읽어들입니다.
            File jsonFile = new File(filePath);

            // JSON 파일의 데이터를 Map<String, List<Integer>>에 매핑합니다.
            Map<String, List<Integer>> map = mapper.readValue(jsonFile, new TypeReference<Map<String, List<Integer>>>(){});

            // Map의 각 entry에 대해 PredictProduct 객체를 만들고, 데이터를 저장합니다.
            for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
                String productName = entry.getKey();
                List<Integer> prices = entry.getValue();

                // product 정보를 설정합니다.
                PredictProduct predictProduct = new PredictProduct();
                Product product = productRepository.findByNameAndDate(productName, LocalDate.now()).get();
                predictProduct.setProduct(product);

                predictProduct.setDay1Price(prices.get(0));
                predictProduct.setDay2Price(prices.get(1));
                predictProduct.setDay3Price(prices.get(2));
                predictProduct.setDay4Price(prices.get(3));
                predictProduct.setDay5Price(prices.get(4));
                predictProduct.setDay6Price(prices.get(5));
                predictProduct.setDay7Price(prices.get(6));

                // PredictProduct 객체를 데이터베이스에 저장합니다.
                predictProductRepository.save(predictProduct);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
