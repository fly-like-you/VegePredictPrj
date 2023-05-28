package com.vegetable.vegetable.vegetable_card;

import com.vegetable.vegetable.vegetable_card.service.VegetableCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vegetable-cards")
public class VegetableCardController {

    private final VegetableCardService vegetableCardService;

    public VegetableCardController(VegetableCardService vegetableCardService) {
        this.vegetableCardService = vegetableCardService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<VegetableCard> getProductByName(@PathVariable String name) {
        Optional<VegetableCard> product = vegetableCardService.getVegetableCardByName(name);

        if (product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }

    }
    @GetMapping
    public ResponseEntity<List<VegetableCard>> getAllVegetableCards() {
        List<VegetableCard> vegetableCards = vegetableCardService.getAllVegetableCards();
        return ResponseEntity.ok(vegetableCards);
    }
}
