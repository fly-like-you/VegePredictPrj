package com.vegetable.vegetable.product.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vegetable.vegetable.PythonScriptExecutor;
import com.vegetable.vegetable.other_site_error.service.OtherSiteErrorRateService;
import com.vegetable.vegetable.predict_product.service.PredictProductService;
import com.vegetable.vegetable.product.Product;
import com.vegetable.vegetable.product.ProductRepository;
import com.vegetable.vegetable.product_index.ProductIndex;
import com.vegetable.vegetable.product_index.service.ProductIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductIndexService productIndexService;

    private final PythonScriptExecutor pythonScriptExecutor;
    @Autowired
    public ProductService(ProductRepository productRepository, ProductIndexService productIndexService, PythonScriptExecutor pythonScriptExecutor) {
        this.productRepository = productRepository;
        this.pythonScriptExecutor = pythonScriptExecutor;
        this.productIndexService = productIndexService;
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> getProductsByNameLastMonth(String name) {
        LocalDate oneMonthAgo = LocalDate.now().minus(1, ChronoUnit.MONTHS);
        return productRepository.findAllFromLastMonth(name, oneMonthAgo);
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
    @Scheduled(cron = "0 35 08 * * ?") // Every day at 11:10
    public void startService(){ // ProductService
        System.out.println("ProductService.startService");
        String pythonScriptPath = "./predict_python/nongnet_crawling/run.py";
        Optional<Integer> exitCode = pythonScriptExecutor.executePythonScript(pythonScriptPath);


        if (exitCode.isPresent() && exitCode.get() == 0) {
            System.out.println("ProductService: 파이썬 정상종료 생성된 JSON을 저장합니다.");
            String filePath = createTodayPath();
            saveProductFromJson(filePath);
            productIndexService.saveProductIndex(LocalDate.now());  // 인덱스 지표 업데이트
        } else {
            System.out.println("ProductService: failed retry 1hour after");
            pythonScriptExecutor.scheduleRetry();
        }
    }

    private static String createTodayPath() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String filePath = "./predict_python/data/price_and_trade/price_trade_" + formattedDate +".json";
        return filePath;
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
            String dateString = (String) data.get("date");
            LocalDate date = LocalDate.parse(dateString);

            for (String vegetable : VEGETABLES) {
                Map<String, Double> productData = (Map<String, Double>) data.get(vegetable);
                int price = productData.get("price").intValue();
                float trade = productData.get("trade").floatValue();
                if (price == 0) {
                    Optional<Product> p = productRepository.findByNameAndDate(vegetable, date.minusDays(1));
                    if (p.isPresent()) {
                        price = p.get().getPrice();
                    }else{
                        price = 1;
                    }

                }
                Product product = new Product(vegetable, price, trade, date);

                if (isProductExist(product)) continue;
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