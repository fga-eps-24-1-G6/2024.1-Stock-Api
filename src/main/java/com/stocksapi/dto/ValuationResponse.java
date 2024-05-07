package com.stocksapi.dto;

public class ValuationResponse {

    private TargetPriceResponse targetPriceResponse;
    private CeilingPriceResponse ceilingPriceResponse;

    public ValuationResponse() {
    }

    public ValuationResponse(TargetPriceResponse targetPriceResponse, CeilingPriceResponse ceilingPriceResponse) {
        this.targetPriceResponse = targetPriceResponse;
        this.ceilingPriceResponse = ceilingPriceResponse;
    }

    public TargetPriceResponse getTargetPriceResponse() {
        return targetPriceResponse;
    }

    public CeilingPriceResponse getCeilingPriceResponse() {
        return ceilingPriceResponse;
    }
}
