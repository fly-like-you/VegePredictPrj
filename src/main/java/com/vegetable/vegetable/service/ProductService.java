package com.vegetable.vegetable.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

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

    public void startService(String startDate, String endDate){
        try {
            // 파이썬 스크립트 실행
            ProcessBuilder pb = new ProcessBuilder("python", "./insertData/run.py");
            pb.inheritIO();

            // 환경 변수 설정
            Map<String, String> env = pb.environment();
            env.put("START_DATE", startDate);
            env.put("END_DATE", endDate);

            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        saveProductFromJson("./insertData/product_"+ startDate +".json");
    }

    // JSON 파일을 읽어들이기
    private String readJsonFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile(filePath);
        Path path = Paths.get(file.getAbsolutePath());
        return Files.readString(path);
    }

    private void saveProductFromJson(String filePath) {
        System.out.println("saveProductFromJson 시작");

        try {
            String jsonData = readJsonFile(filePath);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);

            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String productName = fieldNames.next();
                JsonNode priceArrayNode = rootNode.get(productName);
                List<Product> productList = new ArrayList<>();

                for (JsonNode priceNode : priceArrayNode) {
                    String name = priceNode.get("kindname").asText();
                    String date = priceNode.get("date").asText();
                    int price = priceNode.get("price").asInt();

                    Product product = new Product();
                    product.setName(productName);
                    product.setName(name);
                    product.setDate(LocalDate.parse(date));
                    product.setPrice(price);

                    productList.add(product);
                }

                productRepository.saveAll(productList);
                productRepository.flush();
            }

            System.out.println("Product가 성공적으로 저장되었습니다.");

        } catch (IOException e) {
            System.out.println("파일을 읽어오는 도중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        System.out.println("saveProductFromJson 종료");

    }
    public List<Product> getProductsForSevenDays(LocalDate startDate, String vegetableName) {
        List<Product> productsForSevenDays = new ArrayList<>(Collections.nCopies(7, null));
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            Optional<Product> product = productRepository.findByNameAndDate(vegetableName, currentDate);
            int finalI = i;
            product.ifPresent(value -> productsForSevenDays.set(finalI, value));
        }
        return productsForSevenDays;
    }

    public List<String> getProductsNames(){
        return productRepository.findAllDistinctNames();
    }
}