package com.vegetable.vegetable.controller;
import com.vegetable.vegetable.entity.ErrorRate;
import com.vegetable.vegetable.entity.OtherSiteErrorRate;
import com.vegetable.vegetable.service.ErrorRateService;
import com.vegetable.vegetable.service.OtherSiteErrorRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/error-rate")
public class ErrorRateController {
    private final ErrorRateService errorRateService;
    private final OtherSiteErrorRateService otherSiteErrorRateService;
    @Autowired
    public ErrorRateController(ErrorRateService errorRateService, OtherSiteErrorRateService otherSiteErrorRateService) {
        this.errorRateService = errorRateService;
        this.otherSiteErrorRateService = otherSiteErrorRateService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ErrorRate>> getErrorRateByName(@PathVariable String name) {
        List<ErrorRate> errorRates = errorRateService.getErrorRatesByName(name);
        if (errorRates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(errorRates);
    }

    @GetMapping("other/name/{name}")
    public ResponseEntity<List<OtherSiteErrorRate>> getOtherErrorRateByName(@PathVariable String name) {
        List<OtherSiteErrorRate> otherSiteErrorRate = otherSiteErrorRateService.getOtherSiteErrorRatesByName(name);
        if (otherSiteErrorRate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(otherSiteErrorRate);
    }

    @GetMapping
    public ResponseEntity<List<ErrorRate>> getAllErrorRates() {
        List<ErrorRate> errorRates = errorRateService.getAllErrorRates();
        return new ResponseEntity<>(errorRates, HttpStatus.OK);
    }

}
