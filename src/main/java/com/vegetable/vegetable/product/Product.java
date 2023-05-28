package com.vegetable.vegetable.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "trade")
    private float trade;

    @Column(name = "date")
    private LocalDate date;

    // getters and setters
    public Product(String name, int price, float trade, LocalDate date) {
        this.name = name;
        this.price = price;
        this.trade = trade;
        this.date = date;
    }

    public Product() {

    }
}
