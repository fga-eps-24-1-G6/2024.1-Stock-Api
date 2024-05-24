package com.stocksapi.dto;

import java.math.BigDecimal;

public class YearlyBalanceSheet {
    BigDecimal assets;
    BigDecimal cash;
    BigDecimal costs;
    BigDecimal ebit;
    BigDecimal ebitda;
    BigDecimal equity;
    BigDecimal grossDebt;
    BigDecimal grossProfit;
    BigDecimal liabilities;
    BigDecimal netDebt;
    BigDecimal netProfit;
    BigDecimal netRevenue;
    BigDecimal taxes;
    Integer year;

    public YearlyBalanceSheet(
            BigDecimal assets,
            BigDecimal cash,
            BigDecimal costs,
            BigDecimal ebit,
            BigDecimal ebitda,
            BigDecimal equity,
            BigDecimal grossDebt,
            BigDecimal grossProfit,
            BigDecimal liabilities,
            BigDecimal netDebt,
            BigDecimal netProfit,
            BigDecimal netRevenue,
            BigDecimal taxes,
            Integer year
    ) {
        this.assets = assets;
        this.cash = cash;
        this.costs = costs;
        this.ebit = ebit;
        this.ebitda = ebitda;
        this.equity = equity;
        this.grossDebt = grossDebt;
        this.grossProfit = grossProfit;
        this.liabilities = liabilities;
        this.netDebt = netDebt;
        this.netProfit = netProfit;
        this.netRevenue = netRevenue;
        this.taxes = taxes;
        this.year = year;
    }

    public BigDecimal getAssets() {
        return assets;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public BigDecimal getCosts() {
        return costs;
    }

    public BigDecimal getEbit() {
        return ebit;
    }

    public BigDecimal getEbitda() {
        return ebitda;
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public BigDecimal getGrossDebt() {
        return grossDebt;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public BigDecimal getLiabilities() {
        return liabilities;
    }

    public BigDecimal getNetDebt() {
        return netDebt;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public BigDecimal getNetRevenue() {
        return netRevenue;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public Integer getYear() {
        return year;
    }
}
