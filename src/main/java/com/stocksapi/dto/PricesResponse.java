package com.stocksapi.dto;

import com.stocksapi.model.Prices;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PricesResponse {

    private BigDecimal value;
    private LocalDate priceDate;


    public PricesResponse(Prices prices){
        this.value = prices.getValue();
        this.priceDate = prices.getPriceDate();

    }

    public BigDecimal getValue() {
        return value;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }
}
