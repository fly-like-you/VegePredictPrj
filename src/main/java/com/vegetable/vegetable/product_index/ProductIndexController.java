package com.vegetable.vegetable.product_index;

import com.vegetable.vegetable.product_index.service.ProductIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vegetable-index")
public class ProductIndexController {

    private final ProductIndexService productIndexService;

    @Autowired
    public ProductIndexController(ProductIndexService productIndexService) {
        this.productIndexService = productIndexService;
    }

    @GetMapping
    public ResponseEntity<List<ProductIndex>> getIndex() {
        List<ProductIndex> allProductIndices = productIndexService.getAllLastMonthIndexes();
        return ResponseEntity.ok(allProductIndices);
    }

}
