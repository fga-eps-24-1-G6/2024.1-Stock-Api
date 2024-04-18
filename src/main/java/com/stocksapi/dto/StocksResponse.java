package com.stocksapi.dto;

import com.stocksapi.model.Companies;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class StocksResponse {
    private Integer id;
    private String ticker;
    private Companies companies;
    private BigDecimal freeFloat;
    private BigDecimal tagAlong;
    private Long avgDailyLiquidity;
}
