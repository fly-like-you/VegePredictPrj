package com.vegetable.vegetable.service;
import com.vegetable.vegetable.entity.PredictProduct;
import com.vegetable.vegetable.entity.Product;
import com.vegetable.vegetable.entity.VegetableCard;
import com.vegetable.vegetable.repository.PredictProductRepository;
import com.vegetable.vegetable.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VegetableCardService {
    private final ProductRepository productRepository;
    private final PredictProductRepository predictProductRepository;

    @Autowired
    public VegetableCardService(ProductRepository productRepository, PredictProductRepository predictProductRepository) {
        this.productRepository = productRepository;
        this.predictProductRepository = predictProductRepository;
    }
    public VegetableCard createVegetableCard(String vegetableName) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Product productToday = productRepository.findByNameAndDate(vegetableName, today)
                .orElse(new Product(vegetableName, 0, 0, today));
        Product productYesterday = productRepository.findByNameAndDate(vegetableName, yesterday)
                .orElse(new Product(vegetableName, 0, 0, yesterday));

        double pricePercentage = calculatePricePercentage(productToday.getPrice(), productYesterday.getPrice());

        Optional<PredictProduct> predictProduct = predictProductRepository.findByProductNameAndProductDate(vegetableName, today);
        Optional<Product> product = productRepository.findByNameAndDate(vegetableName, today);

        Product emptyProduct = new Product(vegetableName, 0, 0, today);
        PredictProduct emptyPredict = new PredictProduct(emptyProduct, 0,0,0,0,0,0,0);

        int todayPrice = product.orElse(emptyProduct).getPrice();
        int predictPrice = predictProduct.orElse(emptyPredict).getDay1Price();

        boolean isHigherThanToday = predictPrice >= todayPrice;

        return new VegetableCard(vegetableName, pricePercentage, isHigherThanToday, todayPrice);
    }

    private double calculatePricePercentage(int todayPrice, int yesterdayPrice) {
        return ((double) (todayPrice - yesterdayPrice) / yesterdayPrice) * 100;
    }

    public List<VegetableCard> getAllVegetableCards() {
        List<String> allVegetableNames = productRepository.findAllDistinctNames();
        List<VegetableCard> vegetableCards = new ArrayList<>();

        for (String vegetableName : allVegetableNames) {
            VegetableCard vegetableCard = createVegetableCard(vegetableName);
            vegetableCards.add(vegetableCard);
        }

        return vegetableCards;
    }
    public Optional<VegetableCard> getVegetableCardByName(String name) {
        return Optional.ofNullable(createVegetableCard(name));
    }

}
