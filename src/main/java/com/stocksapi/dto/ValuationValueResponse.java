package com.stocksapi.dto;


import java.math.BigDecimal;

public class ValuationValueResponse {
    private String method;
    private BigDecimal value;


    public ValuationValueResponse(String method, BigDecimal value) {
        this.method = method;
        this.value = value;
    }

    public String getMethod() {
        return method;
    }

    public BigDecimal getValue() {
        return value;
    }
}



