package com.stocksapi.dto;

import java.math.BigDecimal;

public class YearlyPaymentResponse {
    private Integer year;
    private BigDecimal frequency;

    public YearlyPaymentResponse(BigDecimal frequency, Integer year) {
        this.frequency = frequency;
        this.year = year;
    }

    public BigDecimal getFrequency() {
        return frequency;
    }

    public Integer getYear() {
        return year;
    }
}
