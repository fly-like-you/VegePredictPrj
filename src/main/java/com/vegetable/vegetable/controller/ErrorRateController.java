package com.vegetable.vegetable.controller;


import com.vegetable.vegetable.entity.ErrorRate;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.service.ErrorRateService;
import com.vegetable.vegetable.service.ProductService;
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
    @Autowired
    public ErrorRateController(ErrorRateService errorRateService) {
        this.errorRateService = errorRateService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ErrorRate>> getErrorRateByName(@PathVariable String name) {
        List<ErrorRate> errorRates = errorRateService.getErrorRatesByName(name);
        if (errorRates.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(errorRates);
    }

    @GetMapping
    public ResponseEntity<List<ErrorRate>> getAllErrorRates() {
        List<ErrorRate> errorRates = errorRateService.getAllErrorRates();
        return new ResponseEntity<>(errorRates, HttpStatus.OK);
    }

}
