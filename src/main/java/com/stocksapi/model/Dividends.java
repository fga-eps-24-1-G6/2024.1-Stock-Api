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

@Table(name = "dividends", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dividends {
    @ManyToOne
    @JoinColumn(name = "stocks_id", nullable = false)
    private Stocks stocks;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "ownership_date")
    private Date ownershipDate;

    @Column(name = "payment_date")
    private Date paymentDate;
}
