package com.vegetable.vegetable.other_site_error.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vegetable.vegetable.PythonScriptExecutor;
import com.vegetable.vegetable.other_site_error.OtherSiteErrorRate;
import com.vegetable.vegetable.product.Product;
import com.vegetable.vegetable.other_site_error.OtherSiteErrorRateRepository;
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
public class OtherSiteErrorRateService {
    final private OtherSiteErrorRateRepository otherSiteErrorRateRepository;
    final private PythonScriptExecutor pythonScriptExecutor;

    @Autowired
    public OtherSiteErrorRateService(OtherSiteErrorRateRepository otherSiteErrorRateRepository, PythonScriptExecutor pythonScriptExecutor) {
        this.otherSiteErrorRateRepository = otherSiteErrorRateRepository;
        this.pythonScriptExecutor = pythonScriptExecutor;
    }

    public List<OtherSiteErrorRate> getOtherSiteErrorRatesByName(String name) {
        return otherSiteErrorRateRepository.findByName(name);
    }

    @Scheduled(cron = "0 36 08 * * ?")  // Every day at 11:30
    public void runPythonOtherSiteCrawling() throws IOException {

        String jsonFilePath = createTodayPath();
        String pythonScriptPath = "./predict_python/error_rate_crawling/crawlingSite.py";
        System.out.println("OtherSiteErrorRateService.runPythonOtherSiteCrawling");
        Optional<Integer> exitCode = pythonScriptExecutor.executePythonScript(pythonScriptPath);

        if (exitCode.isPresent() && exitCode.get() == 0) {
            System.out.println("OtherSiteErrorRateService: 파이썬 정상종료 생성된 JSON을 저장합니다.");

        } else {
            System.out.println("OtherSiteErrorRateService: failed retry 1hour after");
            pythonScriptExecutor.scheduleRetry();
        }
        saveDataFromJson(jsonFilePath);
    }

    private static String createTodayPath() {
        LocalDate date = LocalDate.now().minusDays(8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
        String jsonFilePath = "./predict_python/data/other_site_error_rates/othSite_prediction_from_" + formattedDate + ".json";
        return jsonFilePath;
    }

    public void saveDataFromJson(String jsonFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> dataList = mapper.readValue(new File(jsonFilePath), List.class);
        ArrayList<OtherSiteErrorRate> rates = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            OtherSiteErrorRate rate = new OtherSiteErrorRate();

            rate.setDate(LocalDate.parse((String)data.get("date")));
            rate.setName((String)data.get("name"));

            rate.setDay1Error((double)data.get("day1error"));
            rate.setDay2Error((double)data.get("day2error"));
            rate.setDay3Error((double)data.get("day3error"));
            rate.setDay4Error((double)data.get("day4error"));
            rate.setDay5Error((double)data.get("day5error"));
            rate.setDay6Error((double)data.get("day6error"));
            rate.setDay7Error((double) data.get("day7error"));

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
