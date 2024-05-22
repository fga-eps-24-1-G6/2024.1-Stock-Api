package com.stocksapi.service;

import com.stocksapi.dto.BalanceSheetsResponse;
import com.stocksapi.dto.YearlyValuesResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.BalanceSheet;
import com.stocksapi.repository.BalanceSheetsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
        Map<Integer, List<BigDecimal>> grossMarginPerYear = new HashMap<>();
        Map<Integer, List<BigDecimal>> netMarginPerYear = new HashMap<>();

        Map<Integer, BigDecimal> netRevenuePerYear = new HashMap<>();
        Map<Integer, BigDecimal> costsPerYear = new HashMap<>();
        Map<Integer, BigDecimal> grossProfitPerYear = new HashMap<>();
        Map<Integer, BigDecimal> netProfitPerYear = new HashMap<>();
        Map<Integer, BigDecimal> ebitdaPerYear = new HashMap<>();
        Map<Integer, BigDecimal> ebitPerYear = new HashMap<>();
        Map<Integer, BigDecimal> taxesPerYear = new HashMap<>();
        Map<Integer, BigDecimal> grossDebtPerYear = new HashMap<>();
        Map<Integer, BigDecimal> netDebtPerYear = new HashMap<>();

        for (BalanceSheet balanceSheet : allByCompaniesId) {
            Integer year = balanceSheet.getYear();
            int quarter = balanceSheet.getQuarter();

            accumulateValue(netRevenuePerYear, year, balanceSheet.getNetRevenue());
            accumulateValue(costsPerYear, year, balanceSheet.getCosts());
            accumulateValue(grossProfitPerYear, year, balanceSheet.getGrossProfit());
            accumulateValue(netProfitPerYear, year, balanceSheet.getNetProfit());
            accumulateValue(ebitdaPerYear, year, balanceSheet.getEbitda());
            accumulateValue(ebitPerYear, year, balanceSheet.getEbit());
            accumulateValue(taxesPerYear, year, balanceSheet.getTaxes());
            accumulateValue(grossDebtPerYear, year, balanceSheet.getGrossDebt());
            accumulateValue(netDebtPerYear, year, balanceSheet.getNetDebt());

            int scale = 10;
            BigDecimal grossMargin = calculateMargin(balanceSheet.getGrossProfit(), balanceSheet.getNetRevenue(), scale);
            addToMarginMap(grossMarginPerYear, year, grossMargin);

            BigDecimal netMargin = calculateMargin(balanceSheet.getNetProfit(), balanceSheet.getNetRevenue(), scale);
            addToMarginMap(netMarginPerYear, year, netMargin);

            if (quarter == 4) {
                addToResponseMap(responseMap, "Patrimônio Líquido", balanceSheet.getEquity(), year);
                addToResponseMap(responseMap, "Ativos", balanceSheet.getAssets(), year);
                addToResponseMap(responseMap, "Passivos", balanceSheet.getLiabilities(), year);
            }
        }

        calculateAndAddAverageMargin(responseMap, grossMarginPerYear, "Margem Bruta");
        calculateAndAddAverageMargin(responseMap, netMarginPerYear, "Margem Líquida");

        addAnnualValuesToResponseMap(responseMap, netRevenuePerYear, "Receita Líquida");
        addAnnualValuesToResponseMap(responseMap, costsPerYear, "Custos");
        addAnnualValuesToResponseMap(responseMap, grossProfitPerYear, "Lucro Bruto");
        addAnnualValuesToResponseMap(responseMap, netProfitPerYear, "Lucro Líquido");
        addAnnualValuesToResponseMap(responseMap, ebitdaPerYear, "EBITDA");
        addAnnualValuesToResponseMap(responseMap, ebitPerYear, "EBIT");
        addAnnualValuesToResponseMap(responseMap, taxesPerYear, "Impostos");
        addAnnualValuesToResponseMap(responseMap, grossDebtPerYear, "Dívida Bruta");
        addAnnualValuesToResponseMap(responseMap, netDebtPerYear, "Dívida Líquida");

        List<BalanceSheetsResponse> balanceSheetsResponseList = new ArrayList<>();
        responseMap.forEach((key, value) -> balanceSheetsResponseList.add(new BalanceSheetsResponse(key, value)));

        return balanceSheetsResponseList;
    }

    private static void accumulateValue(Map<Integer, BigDecimal> map, Integer year, BigDecimal value) {
        value = (value != null) ? value : BigDecimal.ZERO;
        map.put(year, map.getOrDefault(year, BigDecimal.ZERO).add(value));
    }

    private static void addToResponseMap(Map<String, List<YearlyValuesResponse>> responseMap, String key, BigDecimal value, int year) {
        List<YearlyValuesResponse> list = responseMap.getOrDefault(key, new ArrayList<>());
        list.add(new YearlyValuesResponse(value, year));
        responseMap.put(key, list);
    }

    private BigDecimal calculateMargin(BigDecimal numerator, BigDecimal denominator, int scale) {
        if (denominator == null || denominator.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }
        return (numerator != null ? numerator : BigDecimal.ZERO).divide(denominator, scale, RoundingMode.HALF_UP);
    }

    private static void addToMarginMap(Map<Integer, List<BigDecimal>> marginMap, int year, BigDecimal margin) {
        List<BigDecimal> margins = marginMap.getOrDefault(year, new ArrayList<>());
        margins.add(margin);
        marginMap.put(year, margins);
    }

    private static void calculateAndAddAverageMargin(Map<String, List<YearlyValuesResponse>> responseMap, Map<Integer, List<BigDecimal>> marginPerYear, String itemName) {
        for (Map.Entry<Integer, List<BigDecimal>> entry : marginPerYear.entrySet()) {
            Integer year = entry.getKey();
            List<BigDecimal> margins = entry.getValue();
            BigDecimal sum = margins.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal average = sum.divide(new BigDecimal(margins.size()), RoundingMode.HALF_UP);
            List<YearlyValuesResponse> list = responseMap.getOrDefault(itemName, new ArrayList<>());
            list.removeIf(y -> y.getYear() == year);  // Remove existing entry for the same year if it exists
            list.add(new YearlyValuesResponse(average, year));
            responseMap.put(itemName, list);
        }
    }

    private static void addAnnualValuesToResponseMap(Map<String, List<YearlyValuesResponse>> responseMap, Map<Integer, BigDecimal> annualValues, String itemName) {
        for (Map.Entry<Integer, BigDecimal> entry : annualValues.entrySet()) {
            Integer year = entry.getKey();
            BigDecimal value = entry.getValue();
            List<YearlyValuesResponse> list = responseMap.getOrDefault(itemName, new ArrayList<>());
            list.add(new YearlyValuesResponse(value, year));
            responseMap.put(itemName, list);
        }
    }
}
