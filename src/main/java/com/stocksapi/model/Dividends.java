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

@Entity
@Table(name = "dividends", schema = "public")
public class Dividends {
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stocks stocks;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "ownership_date")
    private LocalDate ownershipDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Dividends() {
    }

    public Dividends(Integer id, LocalDate ownershipDate, LocalDate paymentDate, Stocks stocks, String type, BigDecimal value) {
        this.id = id;
        this.ownershipDate = ownershipDate;
        this.paymentDate = paymentDate;
        this.stocks = stocks;
        this.type = type;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getOwnershipDate() {
        return ownershipDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }
}
