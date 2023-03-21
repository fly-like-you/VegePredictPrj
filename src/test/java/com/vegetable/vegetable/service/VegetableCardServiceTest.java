package com.vegetable.vegetable.service;

import com.vegetable.vegetable.entity.PredictProduct;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.entity.VegetableCard;
import com.vegetable.vegetable.repository.PredictProductRepository;
import com.vegetable.vegetable.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VegetableCardServiceTest {

    @InjectMocks
    private VegetableCardService vegetableCardService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PredictProductRepository predictProductRepository;

    private Product productToday;
    private Product productYesterday;
    private PredictProduct predictProduct;

    @BeforeEach
    void setUp() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        productToday = new Product();
        productToday.setId(1L);
        productToday.setName("Tomato");
        productToday.setPrice(150);
        productToday.setDate(today);

        productYesterday = new Product();
        productYesterday.setId(2L);
        productYesterday.setName("Tomato");
        productYesterday.setPrice(100);
        productYesterday.setDate(yesterday);

        predictProduct = new PredictProduct();
        predictProduct.setId(1L);
        predictProduct.setProductId(productToday.getId());
        predictProduct.setIsHigherThanToday(true);
    }

    @Test
    void testCreateVegetableCard() {
        when(productRepository.findByNameAndDate("Tomato", LocalDate.now())).thenReturn(Optional.of(productToday));
        when(productRepository.findByNameAndDate("Tomato", LocalDate.now().minusDays(1))).thenReturn(Optional.of(productYesterday));
        when(predictProductRepository.findByProductId(productToday.getId())).thenReturn(predictProduct);

        VegetableCard vegetableCard = vegetableCardService.createVegetableCard("Tomato");

        assertNotNull(vegetableCard);
        assertEquals("Tomato", vegetableCard.getVegetableName());
        assertEquals(50.0, vegetableCard.getPricePercentage(), 0.01);
        assertTrue(vegetableCard.getIsHigherThanToday());

        verify(productRepository).findByNameAndDate("Tomato", LocalDate.now());
        verify(productRepository).findByNameAndDate("Tomato", LocalDate.now().minusDays(1));
        verify(predictProductRepository).findByProductId(productToday.getId());
    }
}
