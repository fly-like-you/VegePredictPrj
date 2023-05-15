package com.vegetable.vegetable.service;

import com.vegetable.vegetable.entity.ErrorRate;
import com.vegetable.vegetable.repository.ErrorRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class ErrorRateService {
    private final ErrorRateRepository errorRateRepository;
    @Autowired
    public ErrorRateService(ErrorRateRepository errorRateRepository) {
        this.errorRateRepository = errorRateRepository;
    }

    public List<ErrorRate> getErrorRatesByName(String name) {
        return errorRateRepository.findByName(name);
    }

    public List<ErrorRate> getAllErrorRates() {
        return errorRateRepository.findAll();
    }


    // 기타 필요한 메소드들
    public void createSampleErrorRates() {
        LocalDate startDate = LocalDate.of(2023, 3, 21);
        List<String> productNames = Arrays.asList("깻잎(1kg)", "꽈리고추(1kg)", "시금치(1kg)", "딸기(1kg)", "애호박(20개)",
                "양파(1kg)",
                "쥬키니(1kg)",
                "청양고추(1kg)",
                "파프리카(1kg)",
                "풋고추(1kg)");
        Random random = new Random();

        for (String productName : productNames) {
            for (int i = 0; i < 7; i++) {
                LocalDate date = startDate.plusDays(i);

                ErrorRate errorRate = new ErrorRate();
                errorRate.setDate(date);
                errorRate.setName(productName);

                for (int j = 1; j <= 7; j++) {
                    double error = random.nextDouble() * 10;

                    switch (j) {
                        case 1:
                            errorRate.setDay1Error(error);
                            break;
                        case 2:
                            errorRate.setDay2Error(error);
                            break;
                        case 3:
                            errorRate.setDay3Error(error);
                            break;
                        case 4:
                            errorRate.setDay4Error(error);
                            break;
                        case 5:
                            errorRate.setDay5Error(error);
                            break;
                        case 6:
                            errorRate.setDay6Error(error);
                            break;
                        case 7:
                            errorRate.setDay7Error(error);
                            break;
                    }
                }

                errorRateRepository.save(errorRate);
            }
        }
    }
}
