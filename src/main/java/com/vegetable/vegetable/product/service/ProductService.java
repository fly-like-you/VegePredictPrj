package com.vegetable.vegetable.product.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private final ExecutorService service = Executors.newSingleThreadExecutor();
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    private static final List<String> VEGETABLES = List.of(
            "토마토(일반)", "양파(일반)", "파프리카(일반)",
            "시금치(일반)", "깻잎(일반)", "청양",
            "풋고추(전체)", "미나리(일반)"
    );

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    @Async
    @Scheduled(cron = "0 30 10 * * ?") // Every day at 10:30
    public void startService(){
        // service.submit(() ->{
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);

        String filePath = "./predict_python/data/price_and_trade/price_trade_" + formattedDate +".json";
        String pythonPath = "C:/Users/Parkjunho/anaconda3/envs/MachineLearning/python.exe";
        try {
            System.out.println("파이썬 스크립트 실행");
            // 파이썬 스크립트 실행
            ProcessBuilder pb = new ProcessBuilder(pythonPath, "./predict_python/nongnet_crawling/run.py");
            pb.inheritIO();
            pb.start();

            System.out.println("파이썬 스크립트 종료");

        } catch (IOException e) {
            System.out.println("Error during script execution: " + e.getMessage());
            e.printStackTrace();
        }

        saveProductFromJson(filePath);
        // });

    }


    public void saveProductFromJson(String filePath) {
        List<Map<String, Object>> dataList = readJsonData(filePath);
        List<Product> products = processData(dataList);

        productRepository.saveAll(products);
    }

    private List<Map<String, Object>> readJsonData(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽어오는 도중 오류가 발생했습니다: " + filePath, e);
        }
    }

    private List<Product> processData(List<Map<String, Object>> dataList) {
        List<Product> products = new ArrayList<>();

        for (Map<String, Object> data : dataList) {
            LocalDate date = LocalDate.parse((String) data.get("date"));

            for (String vegetable : VEGETABLES) {
                Map<String, Double> productData = (Map<String, Double>) data.get(vegetable);
                int price = productData.get("price").intValue();
                float trade = productData.get("trade").floatValue();
                Product product = new Product(vegetable, price, trade, date);

                if (isProductExist(product)) {
                    continue;
                }
                products.add(product);
            }
        }
        return products;
    }
    private boolean isProductExist(Product product) {
        Optional<Product> productInDb = productRepository.findByNameAndDate(product.getName(), product.getDate());
        return productInDb.isPresent();
    }
    public List<Product> getProductsForSevenDays(LocalDate startDate, String vegetableName) {
        List<Product> productsForSevenDays = new ArrayList<>(Collections.nCopies(7, null));
        for (int i = 0; i < 7; i++) {
            startDate = startDate.plusDays(1);
            System.out.println("startDate" + startDate);
            System.out.println("vegetableName"  + vegetableName);

            Optional<Product> product = productRepository.findByNameAndDate(vegetableName, startDate);
            int finalI = i;
            product.ifPresent(value -> productsForSevenDays.set(finalI, value));
        }
        System.out.println("ProductService.getProductsForSevenDays");
        System.out.println(productsForSevenDays);
        return productsForSevenDays;
    }

    public List<String> getProductsNames(){
        return productRepository.findAllDistinctNames();
    }
}