package com.stocksapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "prices", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prices {

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stocks stocks;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "price_date")
    private Date priceDate;
}
