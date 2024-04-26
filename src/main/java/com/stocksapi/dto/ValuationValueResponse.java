package com.stocksapi.dto;


public class ValuationValueResponse {
    private String method;
    private float value;


    public ValuationValueResponse(String method, float value) {
        this.method = method;
        this.value = value;
    }

    public String getMethod() {
        return method;
    }

    public float getValue() {
        return value;
    }
}



