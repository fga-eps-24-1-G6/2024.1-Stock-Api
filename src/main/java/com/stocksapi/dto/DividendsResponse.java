package com.stocksapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DividendsResponse {
    private String type;
    private BigDecimal payment;
    private LocalDate ownershipDate;
    private LocalDate paymentDate;

    public DividendsResponse(LocalDate ownershipDate, BigDecimal payment, LocalDate paymentDate, String type) {
        this.ownershipDate = ownershipDate;
        this.payment = payment;
        this.paymentDate = paymentDate;
        this.type = type;
    }

    public LocalDate getOwnershipDate() {
        return ownershipDate;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getType() {
        return type;
    }
}
