package com.stocksapi.dto;

import java.math.BigDecimal;

public class PaymentMonthResponse {
    private String month;
    private BigDecimal frequency;

    public PaymentMonthResponse(BigDecimal frequency, String month) {
        this.frequency = frequency;
        this.month = month;
    }

    public BigDecimal getFrequency() {
        return frequency;
    }

    public String getMonth() {
        return month;
    }
}
