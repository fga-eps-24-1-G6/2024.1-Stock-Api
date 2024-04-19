package com.stocksapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
