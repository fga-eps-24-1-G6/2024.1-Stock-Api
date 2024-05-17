package com.stocksapi.dto;

import com.stocksapi.model.Prices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PricesResponse {

    private BigDecimal value;
    private LocalDate priceDate;


    public PricesResponse(Prices prices) {
        this.value = prices.getValue();
        this.priceDate = prices.getPriceDate();

    }
}
