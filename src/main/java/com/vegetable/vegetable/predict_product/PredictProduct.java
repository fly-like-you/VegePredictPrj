package com.vegetable.vegetable.predict_product;

import com.vegetable.vegetable.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PredictProduct")
@Getter @Setter
public class PredictProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "day1_price")
    private Integer day1Price;

    @Column(name = "day2_price")
    private Integer day2Price;

    @Column(name = "day3_price")
    private Integer day3Price;

    @Column(name = "day4_price")
    private Integer day4Price;


    @Column(name = "day5_price")
    private Integer day5Price;

    @Column(name = "day6_price")
    private Integer day6Price;

    @Column(name = "day7_price")
    private Integer day7Price;

    public PredictProduct(String name, LocalDate date, Integer day1Price, Integer day2Price, Integer day3Price, Integer day4Price, Integer day5Price, Integer day6Price, Integer day7Price) {
        this.name = name;
        this.date = date;
        this.day1Price = day1Price;
        this.day2Price = day2Price;
        this.day3Price = day3Price;
        this.day4Price = day4Price;
        this.day5Price = day5Price;
        this.day6Price = day6Price;
        this.day7Price = day7Price;
    }

    public PredictProduct(){

    }
// getters and setters
}
