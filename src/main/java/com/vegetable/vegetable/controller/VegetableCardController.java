package com.vegetable.vegetable.controller;

import com.vegetable.vegetable.entity.VegetableCard;
import com.vegetable.vegetable.service.VegetableCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vegetable-cards")
public class VegetableCardController {

    private final VegetableCardService vegetableCardService;

    public VegetableCardController(VegetableCardService vegetableCardService) {
        this.vegetableCardService = vegetableCardService;
    }

    @GetMapping
    public ResponseEntity<List<VegetableCard>> getAllVegetableCards() {
        List<VegetableCard> vegetableCards = vegetableCardService.getAllVegetableCards();
        return ResponseEntity.ok(vegetableCards);
    }
}
