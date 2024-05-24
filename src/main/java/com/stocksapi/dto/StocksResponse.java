package com.stocksapi.dto;

import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Getter
public class StocksResponse {
    private String ticker;
    private BigDecimal currentPrice;
    private String companyName;
    private Integer companyId;
    private BigDecimal freeFloat;
    private BigDecimal tagAlong;
    private BigInteger avgDailyLiquidity;
    private String categorie;
    private BigDecimal variationOneDay;
    private BigDecimal variationOneMonth;
    private BigDecimal variationTwelveMonths;
    private List<PricesResponse> allPrices;

    public StocksResponse(Stocks stocks, Prices prices, String categorie, BigDecimal variationOneDay, BigDecimal variationOneMonth, BigDecimal variationTwelveMonths, String companyName, Integer companyId, List<PricesResponse> allPrices) {
        this.ticker = stocks.getTicker();
        this.companyName = companyName;
        this.companyId = companyId;
        this.currentPrice = new PricesResponse(prices).getValue();
        this.freeFloat = stocks.getFreeFloat();
        this.tagAlong = stocks.getTagAlong();
        this.avgDailyLiquidity = stocks.getAvgDailyLiquidity();
        this.categorie = categorie;
        this.variationOneDay = variationOneDay;
        this.variationOneMonth = variationOneMonth;
        this.variationTwelveMonths = variationTwelveMonths;
        this.allPrices = allPrices;
    }
}