package com.stocksapi.dto;

import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StocksResponse {
    private String ticker;
    private BigDecimal currentPrice;
    private String companyName;
    private BigDecimal freeFloat;
    private BigDecimal tagAlong;
    private BigInteger avgDailyLiquidity;
    private String categorie;
    private BigDecimal variationOneDay;
    private BigDecimal variationOneMonth;
    private BigDecimal variationTwelveMonths;


    public StocksResponse(Stocks stocks, Prices prices, String categorie, BigDecimal variationOneDay, BigDecimal variationOneMonth, BigDecimal variationTwelveMonths, String companyName) {
        this.ticker = stocks.getTicker();
        this.companyName = companyName;
        this.currentPrice = new PricesResponse(prices).getValue();
        this.freeFloat = stocks.getFreeFloat();
        this.tagAlong = stocks.getTagAlong();
        this.avgDailyLiquidity = stocks.getAvgDailyLiquidity();
        this.categorie = categorie;
        this.variationOneDay = variationOneDay;
        this.variationOneMonth = variationOneMonth;
        this.variationTwelveMonths = variationTwelveMonths;
    }

    public BigInteger getAvgDailyLiquidity() {
        return avgDailyLiquidity;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getCompanyName() {
        return companyName;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public BigDecimal getFreeFloat() {
        return freeFloat;
    }

    public BigDecimal getTagAlong() {
        return tagAlong;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getVariationOneDay() {
        return variationOneDay;
    }

    public BigDecimal getVariationOneMonth() {
        return variationOneMonth;
    }

    public BigDecimal getVariationTwelveMonths() {
        return variationTwelveMonths;
    }
}