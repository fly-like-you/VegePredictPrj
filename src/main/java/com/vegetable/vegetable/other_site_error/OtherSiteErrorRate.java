package com.vegetable.vegetable.other_site_error;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "OtherSiteErrorRate")
@Getter
@Setter
public class OtherSiteErrorRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "name")
    private String name;

    @Column(name = "day1_error")
    private Double day1Error;

    @Column(name = "day2_error")
    private Double day2Error;

    @Column(name = "day3_error")
    private Double day3Error;

    @Column(name = "day4_error")
    private Double day4Error;

    @Column(name = "day5_error")
    private Double day5Error;

    @Column(name = "day6_error")
    private Double day6Error;

    @Column(name = "day7_error")
    private Double day7Error;

}
