package com.stocksapi.dto;

public class TargetPriceResponse {

    private String method;
    private Double value;

    public TargetPriceResponse() {
    }

    public TargetPriceResponse(Double value, String method) {
        this.value = value;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public Double getValue() {
        return value;
    }
}
