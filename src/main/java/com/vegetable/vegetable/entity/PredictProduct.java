package com.vegetable.vegetable.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PredictProduct")
@Getter @Setter
public class PredictProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "name", referencedColumnName = "name"),
            @JoinColumn(name = "date", referencedColumnName = "date")
    })
    private Product product;

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

    // getters and setters
}
