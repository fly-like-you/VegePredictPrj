package com.vegetable.vegetable.error_rate.service;

import com.vegetable.vegetable.error_rate.ErrorRate;
import com.vegetable.vegetable.error_rate.ErrorRateController;
import com.vegetable.vegetable.error_rate.ErrorRateRepository;
import com.vegetable.vegetable.predict_product.PredictProduct;
import com.vegetable.vegetable.product.Product;
import com.vegetable.vegetable.predict_product.service.PredictProductService;
import com.vegetable.vegetable.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

import static com.vegetable.vegetable.error_rate.service.ErrorRateCalculator.calc;

@Service
public class ErrorRateService {
    // insert, delete, update
    private final ErrorRateRepository errorRateRepository;
    private final ProductService productService;
    private final PredictProductService predictProductService;

    @Autowired
    public ErrorRateService(ErrorRateRepository errorRateRepository, ProductService productService,
                            PredictProductService predictProductService) {
        this.errorRateRepository = errorRateRepository;
        this.productService = productService;
        this.predictProductService = predictProductService;

    }

    private static final List<String> VEGETABLES = List.of(
            "토마토(일반)", "양파(일반)", "파프리카(일반)",
            "시금치(일반)", "깻잎(일반)", "청양",
            "풋고추(전체)", "미나리(일반)"
    );

    public List<ErrorRate> getErrorRatesByName(String name) {
        return errorRateRepository.findByName(name);
    }

    public List<ErrorRate> getAllErrorRates() {
        return errorRateRepository.findAll();
    }

    @Scheduled(cron = "0 30 16 * * ?")  // Every day at 16:30
    public void saveErrorRateScheduled() {
        for (String name : VEGETABLES) {
            saveErrorRate(LocalDate.now().minusDays(7), name);
        }
    }
    public void saveErrorRate(LocalDate date, String productName) {
        ErrorRate errorRate = new ErrorRate(date, productName);
        List<Double> errorRates = calcErrorRate(date, productName);

        errorRate.setDay1Error(errorRates.get(0));
        errorRate.setDay2Error(errorRates.get(1));
        errorRate.setDay3Error(errorRates.get(2));
        errorRate.setDay4Error(errorRates.get(3));
        errorRate.setDay5Error(errorRates.get(4));
        errorRate.setDay6Error(errorRates.get(5));
        errorRate.setDay7Error(errorRates.get(6));

        if (isExistRate(errorRate)) return;

        errorRateRepository.save(errorRate);
    }
    private boolean isExistRate(ErrorRate rate) {
        Optional<ErrorRate> errorRateInDb = errorRateRepository.findByNameAndDate(rate.getName(), rate.getDate());
        return errorRateInDb.isPresent();
    }


    // 에러율을 측정 (한 날자를 기준으로 7일간의 예측된 데이터와 실제 데이터의 오차율을 구함)
    private List<Double> calcErrorRate(LocalDate date, String productName) {
        PredictProduct predictProduct;
        List<Product> products = productService.getProductsForSevenDays(date, productName);
        Optional<PredictProduct> predictProductOptional = predictProductService.getPredictProduct(date, productName);
        
        if (predictProductOptional.isPresent()) {
            predictProduct = predictProductOptional.get();
        } else{
            return new ArrayList<Double>(Collections.nCopies(7, 100.0));
        }

        List<Double> errorRates = calc(predictProduct, products);
        return errorRates;
    }




}
