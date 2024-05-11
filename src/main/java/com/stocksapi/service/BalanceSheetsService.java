package com.stocksapi.service;

import com.stocksapi.dto.BalanceSheetsResponse;
import com.stocksapi.dto.YearlyValuesResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.BalanceSheet;
import com.stocksapi.repository.BalanceSheetsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceSheetsService {
    private final BalanceSheetsRepository balanceSheetsRepository;

    public BalanceSheetsService(BalanceSheetsRepository balanceSheetsRepository) {
        this.balanceSheetsRepository = balanceSheetsRepository;
    }

    public List<BalanceSheetsResponse> getByCompanyId(Integer companyId) {
        List<BalanceSheet> allByCompaniesId = balanceSheetsRepository.findAllByCompaniesId(companyId);

        if (allByCompaniesId.isEmpty()) {
            throw new BadRequestNotFoundException(404, "Could not find Balance Sheets from Company with id: " + companyId);
        }

        Map<String, List<YearlyValuesResponse>> responseMap = new HashMap<>();

        for (BalanceSheet balanceSheet : allByCompaniesId) {
            addToResponseMap(responseMap, "Receita Líquida", balanceSheet.getNetRevenue(), balanceSheet.getYear());
            addToResponseMap(responseMap, "Custos", balanceSheet.getCosts(), balanceSheet.getYear());
            addToResponseMap(responseMap, "Lucro Bruto", balanceSheet.getGrossProfit(), balanceSheet.getYear());
            addToResponseMap(responseMap, "Lucro Líquido", balanceSheet.getNetProfit(), balanceSheet.getYear());
            addToResponseMap(responseMap, "EBITDA", balanceSheet.getEbitda(), balanceSheet.getYear());
            addToResponseMap(responseMap, "EBIT", balanceSheet.getEbit(), balanceSheet.getYear());
            addToResponseMap(responseMap, "Impostos", balanceSheet.getTaxes(), balanceSheet.getYear());
            addToResponseMap(responseMap, "Dívida Bruta", balanceSheet.getGrossDebt(), balanceSheet.getYear());
            addToResponseMap(responseMap, "Dívida Líquida", balanceSheet.getNetDebt(), balanceSheet.getYear());

            int scale = 10;
            BigDecimal grossMargin = calculateMargin(balanceSheet.getGrossProfit(), balanceSheet.getNetRevenue(), scale);
            addToResponseMap(responseMap, "Margem Bruta", grossMargin, balanceSheet.getYear());

            BigDecimal netMargin = calculateMargin(balanceSheet.getNetProfit(), balanceSheet.getNetRevenue(), scale);
            addToResponseMap(responseMap, "Margem Líquida", netMargin, balanceSheet.getYear());

            addToResponseMap(responseMap, "Patrimônio Líquido", balanceSheet.getEquity(), balanceSheet.getYear());
            addToResponseMap(responseMap, "Ativos", balanceSheet.getAssets(), balanceSheet.getYear());
            addToResponseMap(responseMap, "Passivos", balanceSheet.getLiabilities(), balanceSheet.getYear());
        }

        List<BalanceSheetsResponse> balanceSheetsResponseList = new ArrayList<>();
        responseMap.forEach((key, value) -> balanceSheetsResponseList.add(new BalanceSheetsResponse(key, value)));

        return balanceSheetsResponseList;
    }

    private void addToResponseMap(Map<String, List<YearlyValuesResponse>> responseMap, String key, BigDecimal value, int year) {
        List<YearlyValuesResponse> list = responseMap.getOrDefault(key, new ArrayList<>());
        list.add(new YearlyValuesResponse(value, year));
        responseMap.put(key, list);
    }

    private BigDecimal calculateMargin(BigDecimal numerator, BigDecimal denominator, int scale) {
        return numerator.divide(denominator, scale, RoundingMode.HALF_UP);
    }
}
