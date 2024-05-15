package com.stocksapi.dto;

import java.math.BigDecimal;

public class DividendYieldResponse {
    private BigDecimal current;
    private BigDecimal avgFiveYears;
    private BigDecimal avgTenYears;

    public DividendYieldResponse(BigDecimal avgFiveYears, BigDecimal avgTenYears, BigDecimal current) {
        this.avgFiveYears = avgFiveYears;
        this.avgTenYears = avgTenYears;
        this.current = current;
    }

    public BigDecimal getAvgFiveYears() {
        return avgFiveYears;
    }

    public BigDecimal getAvgTenYears() {
        return avgTenYears;
    }

    public BigDecimal getCurrent() {
        return current;
    }
}
