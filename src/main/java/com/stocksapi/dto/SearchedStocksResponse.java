package com.stocksapi.dto;

import java.math.BigDecimal;

public class SearchedStocksResponse {

    private Integer id;
    private String ticker;
    private Integer companyId;
    private String companyName;
    private BigDecimal lastPrice;
    private BigDecimal variationOneDay;

    public SearchedStocksResponse() {
    }

    public SearchedStocksResponse(Integer companyId, String companyName, Integer id, BigDecimal lastPrice, String ticker, BigDecimal variationOneDay) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.id = id;
        this.lastPrice = lastPrice;
        this.ticker = ticker;
        this.variationOneDay = variationOneDay;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getVariationOneDay() {
        return variationOneDay;
    }
}
