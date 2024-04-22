package com.stocksapi.dto;

import com.stocksapi.model.Prices;

import java.math.BigDecimal;
import java.util.Date;

public class PricesResponse {

    private BigDecimal value;
    private Date priceDate;


    public PricesResponse(Prices prices){
        this.value = prices.getValue();
        this.priceDate = prices.getPriceDate();

    }

    public BigDecimal getValue() {
        return value;
    }

    public Date getPriceDate() {
        return priceDate;
    }
}
