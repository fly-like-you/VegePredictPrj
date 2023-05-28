package com.vegetable.vegetable.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vegetable.vegetable.entity.OtherSiteErrorRate;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.repository.OtherSiteErrorRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class OtherSiteErrorRateService {
    final private OtherSiteErrorRateRepository otherSiteErrorRateRepository;

    @Autowired
    public OtherSiteErrorRateService(OtherSiteErrorRateRepository otherSiteErrorRateRepository) {
        this.otherSiteErrorRateRepository = otherSiteErrorRateRepository;
    }

    public List<OtherSiteErrorRate> getOtherSiteErrorRatesByName(String name) {
        return otherSiteErrorRateRepository.findByName(name);
    }

    @Async
    @Scheduled(cron = "0 30 14 * * ?")  // Every day at 14:30
    public void runPythonOtherSiteCrawling() throws IOException {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);

        String pythonPath = "C:/Users/Parkjunho/anaconda3/envs/MachineLearning/python.exe";
        String jsonFilePath = "./predict_python/data/other_site_error_rates/othSite_prediction_from_" + formattedDate + ".json";
        try {
            // 파이썬 스크립트 실행
            ProcessBuilder pb = new ProcessBuilder(pythonPath, "./predict_python/error_rate_crawling/crawlingSite.py");
            pb.inheritIO();
            pb.start();

        } catch (IOException e) {
            System.out.println("Error during script execution: " + e.getMessage());
            e.printStackTrace();
        }
        saveDataFromJson(jsonFilePath);
    }

    public void saveDataFromJson(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> dataList = mapper.readValue(new File(jsonFilePath), List.class);
        ArrayList<OtherSiteErrorRate> rates = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            OtherSiteErrorRate rate = new OtherSiteErrorRate();

            rate.setDate(LocalDate.parse((String)data.get("date")));
            rate.setName((String)data.get("name"));

            rate.setDay1Error((Double)data.get("day1error"));
            rate.setDay2Error((Double)data.get("day2error"));
            rate.setDay3Error((Double)data.get("day3error"));
            rate.setDay4Error((Double)data.get("day4error"));
            rate.setDay5Error((Double)data.get("day5error"));
            rate.setDay6Error((Double)data.get("day6error"));
            rate.setDay7Error((Double) data.get("day7error"));

            if (isExistRate(rate)) {
                continue;
            }

            rates.add(rate);
        }
        otherSiteErrorRateRepository.saveAll(rates);
    }
    private boolean isExistRate(OtherSiteErrorRate rate) {
        Optional<Product> productInDb = otherSiteErrorRateRepository.findByNameAndDate(rate.getName(), rate.getDate());
        return productInDb.isPresent();
    }

}
