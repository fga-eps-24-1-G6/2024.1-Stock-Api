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
    private Stocks stockId;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "price_date", nullable = false)
    private LocalDate priceDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Prices() {
    }

    public Prices(Integer id, LocalDate priceDate, Stocks stockId, BigDecimal value) {
        this.id = id;
        this.priceDate = priceDate;
        this.stockId = stockId;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public Stocks getStockId() {
        return stockId;
    }

    public BigDecimal getValue() {
        return value;
    }
}