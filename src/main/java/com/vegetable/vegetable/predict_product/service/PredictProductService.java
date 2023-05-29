package com.vegetable.vegetable.predict_product.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vegetable.vegetable.PythonScriptExecutor;
import com.vegetable.vegetable.error_rate.service.ErrorRateService;
import com.vegetable.vegetable.predict_product.PredictProduct;
import com.vegetable.vegetable.predict_product.PredictProductRepository;
import com.vegetable.vegetable.product.Product;
import com.vegetable.vegetable.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PredictProductService {

    private final PredictProductRepository predictProductRepository;
    private final PythonScriptExecutor pythonScriptExecutor;

    @Autowired
    public PredictProductService(PredictProductRepository predictProductRepository, PythonScriptExecutor pythonScriptExecutor) {
        this.predictProductRepository = predictProductRepository;
        this.pythonScriptExecutor = pythonScriptExecutor;

    }

    public Optional<PredictProduct> getPredictProduct(LocalDate date, String vegetableName) {
        return predictProductRepository.findByNameAndDate(vegetableName, date);
    }

    @Scheduled(cron = "0 37 08 * * ?") // Every day at 11:40
    public void runPythonDeepLearning() {
        System.out.println("PredictProductService: 머신러닝 실행");
        String jsonFilePath = createTodayPath();
        String pythonScriptPath =  "./predict_python/price_predict/MachineLearning.py";

        Optional<Integer> exitCode = pythonScriptExecutor.executePythonScript(pythonScriptPath);

        if (exitCode.isPresent() && exitCode.get() == 0) {
            System.out.println("PredictProductService: 파이썬 정상종료 생성된 JSON을 저장합니다.");
            savePredictionFromJson(jsonFilePath);
        } else {
            System.out.println("PredictProductService: failed retry 1hour after");
            pythonScriptExecutor.scheduleRetry();
        }
    }

    private static String createTodayPath() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String jsonFilePath = "./predict_python/data/predict/predict_from_" + formattedDate +".json";
        return jsonFilePath;
    }

    public void savePredictionFromJson(String filePath) {
        System.out.println("PredictProductService.savePredictionFromJson 실행");
        ObjectMapper mapper = new ObjectMapper();

        try {
            // JSON 파일을 읽어들입니다.
            File jsonFile = new File(filePath);

            // JSON 파일의 데이터를 Map
            Map<String, List<Integer>> map = mapper.readValue(jsonFile, new TypeReference<Map<String, List<Integer>>>(){});

            // Map의 각 entry에 대해 PredictProduct 객체를 만들고, 데이터를 저장합니다.
            for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
                String productName = entry.getKey();
                List<Integer> prices = entry.getValue();

                // product 정보를 설정합니다.
                PredictProduct predictProduct = new PredictProduct();
                predictProduct.setName(productName);
                predictProduct.setDate(LocalDate.now());

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
