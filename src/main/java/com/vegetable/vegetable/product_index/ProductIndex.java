package com.vegetable.vegetable.product_index;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class ProductIndex {
    public ProductIndex(LocalDate date, double productIndex) {
        this.date = date;
        this.productIndex = productIndex;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "productIndex")
    private double productIndex;

    public ProductIndex() {

    }
}
