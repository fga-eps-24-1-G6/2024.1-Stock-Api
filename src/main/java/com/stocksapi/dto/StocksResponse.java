package com.stocksapi.dto;

import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;

import java.math.BigDecimal;

public class StocksResponse {
    private String ticker;
    private BigDecimal currentPrice;
    private String companyName;
    private BigDecimal freeFloat;
    private BigDecimal tagAlong;
    private Long avgDailyLiquidity;

    public StocksResponse(Stocks stocks, Prices prices) {
        this.ticker = stocks.getTicker();
        this.companyName = new CompaniesResponse(stocks.getCompanies()).getName();
        this.currentPrice = new PricesResponse(prices).getValue();
        this.freeFloat = stocks.getFreeFloat();
        this.tagAlong = stocks.getTagAlong();
        this.avgDailyLiquidity = stocks.getAvgDailyLiquidity();
    }

    public BigDecimal getFreeFloat() {
        return freeFloat;
    }

    public BigDecimal getTagAlong() {
        return tagAlong;
    }

    public Long getAvgDailyLiquidity() {
        return avgDailyLiquidity;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
}