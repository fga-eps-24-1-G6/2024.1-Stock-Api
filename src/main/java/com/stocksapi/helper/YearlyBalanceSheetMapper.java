package com.stocksapi.helper;

import com.stocksapi.dto.YearlyBalanceSheet;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class YearlyBalanceSheetMapper {
    public static List<YearlyBalanceSheet> mapToYearlyBalanceSheets(List<Object[]> rawResults) {
        return rawResults.stream().map(result -> new YearlyBalanceSheet(
                (BigDecimal) result[11], // assets
                (BigDecimal) result[10], // cash
                (BigDecimal) result[2],  // costs
                (BigDecimal) result[6],  // ebit
                (BigDecimal) result[5],  // ebitda
                (BigDecimal) result[9],  // equity
                (BigDecimal) result[7],  // grossDebt
                (BigDecimal) result[3],  // grossProfit
                (BigDecimal) result[12], // liabilities
                (BigDecimal) result[8],  // netDebt
                (BigDecimal) result[4],  // netProfit
                (BigDecimal) result[1],  // netRevenue
                (BigDecimal) result[7],  // taxes
                (Integer) result[0]      // year
        )).collect(Collectors.toList());
    }
}
