package com.stocksapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "prices", schema = "public")
@Entity
public class Prices {

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stocks stocks;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "price_date")
    private LocalDate priceDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Prices() {
    }

    public Prices(LocalDate priceDate, Stocks stocks, BigDecimal value, Integer id) {
        this.priceDate = priceDate;
        this.stocks = stocks;
        this.value = value;
        this.id = id;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getId() {
        return id;
    }
}
