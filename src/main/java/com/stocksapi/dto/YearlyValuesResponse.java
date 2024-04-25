package com.stocksapi.dto;

import java.math.BigDecimal;

public class YearlyValuesResponse {
    private Integer year;
    private BigDecimal value;

    public YearlyValuesResponse(BigDecimal value, Integer year) {
        this.value = value;
        this.year = year;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getYear() {
        return year;
    }
}
