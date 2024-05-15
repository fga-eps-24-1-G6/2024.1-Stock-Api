package com.stocksapi.dto;

import java.math.BigDecimal;

public class SearchedStocksResponse {
    private Integer id;
    private String ticker;
    private Integer companyId;
    private String companyName;
    private BigDecimal lastPrice;
    private BigDecimal variationOneDay;
    private String sector;

    public SearchedStocksResponse() {
    }

    public SearchedStocksResponse(Integer companyId, String companyName, Integer id, BigDecimal lastPrice, String sector, String ticker, BigDecimal variationOneDay) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.id = id;
        this.lastPrice = lastPrice;
        this.sector = sector;
        this.ticker = ticker;
        this.variationOneDay = variationOneDay;
    }

    public SearchedStocksResponse(Integer id, String ticker, String companyName, String sector, Integer companyId) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.id = id;
        this.ticker = ticker;
        this.sector = sector;
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

    public String getSector() {
        return sector;
    }
}
