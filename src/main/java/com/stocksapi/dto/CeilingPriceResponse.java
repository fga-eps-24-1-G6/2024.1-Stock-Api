package com.stocksapi.dto;

import java.math.BigDecimal;

public class CeilingPriceResponse {

    private String method;
    private BigDecimal value;

    public CeilingPriceResponse() {
    }

    public CeilingPriceResponse(BigDecimal value, String method) {
        this.value = value;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public BigDecimal getValue() {
        return value;
    }

}
