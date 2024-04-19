package com.stocksapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stocks", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "ticker")
    private String ticker;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Companies companies;

    @Column(name = "free_float")
    private BigDecimal freeFloat;

    @Column(name = "tag_along")
    private BigDecimal tagAlong;

    @Column(name = "avg_daily_liquidity")
    private Long avgDailyLiquidity;
}
