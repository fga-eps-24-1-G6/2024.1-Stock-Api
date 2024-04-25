package com.stocksapi.dto;

import java.util.List;

public class BalanceSheetsResponse {
    private String item;
    private List<YearlyValuesResponse> yearlyValues;

    public BalanceSheetsResponse(String item, List<YearlyValuesResponse> yearlyValues) {
        this.item = item;
        this.yearlyValues = yearlyValues;
    }

    public String getItem() {
        return item;
    }

    public List<YearlyValuesResponse> getYearlyValues() {
        return yearlyValues;
    }
}
