package com.stocksapi.dto;

import com.stocksapi.model.Dividends;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DividendsWithDividendYieldResponse {
    private List<Dividends> dividends;
    private Map<Integer, BigDecimal> yearlyPayments;
    private Map<String, Double> paymentMonths;
    private Map<String, BigDecimal> dividendYield;
}
