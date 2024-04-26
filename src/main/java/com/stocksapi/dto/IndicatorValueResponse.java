package com.stocksapi.dto;

import java.math.BigDecimal;

public class IndicatorValueResponse {
    private String indicator;
    private BigDecimal value;

    public IndicatorValueResponse(String indicator, BigDecimal value) {
        this.indicator = indicator;
        this.value = value;
    }

    public String getIndicator() {
        return indicator;
    }

    public BigDecimal getValue() {
        return value;
    }
}
