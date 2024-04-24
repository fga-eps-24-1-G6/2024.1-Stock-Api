package com.stocksapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "prices", schema = "public")
@Entity
public class Prices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "price_date", nullable = false)
    private LocalDate priceDate;

    public Prices() {
    }

    public Prices(LocalDate priceDate, Integer stockId, BigDecimal value) {
        this.priceDate = priceDate;
        this.stockId = stockId;
        this.value = value;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public Integer getStockId() {
        return stockId;
    }

    public BigDecimal getValue() {
        return value;
    }
}