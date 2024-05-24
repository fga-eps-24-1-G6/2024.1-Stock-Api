package com.stocksapi.service;

import com.stocksapi.dto.BalanceSheetsResponse;
import com.stocksapi.dto.YearlyBalanceSheet;
import com.stocksapi.dto.YearlyValuesResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.helper.YearlyBalanceSheetMapper;
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

    public List<YearlyBalanceSheet> getYearlyByCompanyId(Integer companyId) {
        List<Object[]> rawResults = balanceSheetsRepository.findYearlyByCompanyId(companyId);
        if (rawResults.isEmpty()) {
            throw new BadRequestNotFoundException(404, "Could not find Balance Sheets from Company with id: " + companyId);
        }

        return YearlyBalanceSheetMapper.mapToYearlyBalanceSheets(rawResults);
    }

    public List<List<Object>> getFormattedYearlyByCompanyId(Integer companyId) {
        List<YearlyBalanceSheet> yearlyValues = this.getYearlyByCompanyId(companyId);

        List<List<Object>> yearlySheet = new ArrayList<>();
        List<Object> header = new ArrayList<>();
        header.add("");
        List<Object> yearlyNetRevenue = new ArrayList<>();
        yearlyNetRevenue.add("Receita Líquida");
        List<Object> yearlyNetProfit = new ArrayList<>();
        yearlyNetProfit.add("Lucro Líquido");
        List<Object> yearlyGrossProfit = new ArrayList<>();
        yearlyGrossProfit.add("Lucro Bruto");
        List<Object> yearlyEbit = new ArrayList<>();
        yearlyEbit.add("EBIT");
        List<Object> yearlyEbitda = new ArrayList<>();
        yearlyEbitda.add("EBITDA");
        List<Object> yearlyNetDebt = new ArrayList<>();
        yearlyNetDebt.add("Dívida Líquida");
        List<Object> yearlyGrossDebt = new ArrayList<>();
        yearlyGrossDebt.add("Dívida Bruta");
        List<Object> yearlyCosts = new ArrayList<>();
        yearlyCosts.add("Custos");
        List<Object> yearlyTaxes = new ArrayList<>();
        yearlyTaxes.add("Impostos");
        List<Object> yearlyEquity = new ArrayList<>();
        yearlyEquity.add("Patrimônio Líquido");
        List<Object> yearlyAssets = new ArrayList<>();
        yearlyAssets.add("Ativos");
        List<Object> yearlyLiabilities = new ArrayList<>();
        yearlyLiabilities.add("Passivos");

        for(YearlyBalanceSheet sheet : yearlyValues){
            header.add(sheet.getYear());
            yearlyNetRevenue.add(sheet.getNetRevenue());
            yearlyNetProfit.add(sheet.getNetProfit());
            yearlyGrossProfit.add(sheet.getGrossProfit());
            yearlyEbit.add(sheet.getEbit());
            yearlyEbitda.add(sheet.getEbitda());
            yearlyNetDebt.add(sheet.getNetDebt());
            yearlyGrossDebt.add(sheet.getGrossDebt());
            yearlyCosts.add(sheet.getCosts());
            yearlyTaxes.add(sheet.getTaxes());
            yearlyEquity.add(sheet.getEquity());
            yearlyAssets.add(sheet.getAssets());
            yearlyLiabilities.add(sheet.getLiabilities());
        }

        yearlySheet.add(header);
        yearlySheet.add(yearlyNetRevenue);
        yearlySheet.add(yearlyNetProfit);
        yearlySheet.add(yearlyGrossProfit);
        yearlySheet.add(yearlyEbit);
        yearlySheet.add(yearlyEbitda);
        yearlySheet.add(yearlyNetDebt);
        yearlySheet.add(yearlyGrossDebt);
        yearlySheet.add(yearlyCosts);
        yearlySheet.add(yearlyTaxes);
        yearlySheet.add(yearlyEquity);
        yearlySheet.add(yearlyAssets);
        yearlySheet.add(yearlyLiabilities);

        return yearlySheet;
    }

    public YearlyBalanceSheet getLastTwelveMonthsResultsByCompanyId(Integer companyId) {
        BalanceSheet[] orderedBalanceSheets = balanceSheetsRepository.findLatestBalanceSheetByCompanyId(companyId);
        BalanceSheet[] lastFour = new BalanceSheet[Math.min(orderedBalanceSheets.length, 4)];
        System.arraycopy(orderedBalanceSheets, 0, lastFour, 0, lastFour.length);

        YearlyBalanceSheet lastTwelveMonthsResults;

        BigDecimal netRevenue = BigDecimal.ZERO;
        BigDecimal costs = BigDecimal.ZERO;
        BigDecimal grossProfit = BigDecimal.ZERO;
        BigDecimal netProfit = BigDecimal.ZERO;
        BigDecimal ebitda = BigDecimal.ZERO;
        BigDecimal ebit = BigDecimal.ZERO;
        BigDecimal taxes = BigDecimal.ZERO;

        BigDecimal grossMargin = BigDecimal.ZERO;
        BigDecimal netMargin = BigDecimal.ZERO;

        int scale = 10;
        for (BalanceSheet balanceSheet : lastFour) {
            if(balanceSheet.getNetRevenue()!=null){
                netRevenue = netRevenue.add(balanceSheet.getNetRevenue());
            }
            if(balanceSheet.getCosts()!=null){
                costs = costs.add(balanceSheet.getCosts());
            }
            if(balanceSheet.getGrossProfit()!=null){
                grossProfit = grossProfit.add(balanceSheet.getGrossProfit());
            }
            if(balanceSheet.getNetProfit()!=null){
                netProfit = netProfit.add(balanceSheet.getNetProfit());
            }
            if(balanceSheet.getEbitda()!=null){
                ebitda = ebitda.add(balanceSheet.getEbitda());
            }
            if(balanceSheet.getEbit()!=null){
                ebit = ebit.add(balanceSheet.getEbit());
            }
            if(balanceSheet.getTaxes()!=null){
                taxes = taxes.add(balanceSheet.getTaxes());
            }
            if(balanceSheet.getGrossProfit()!=null && balanceSheet.getNetRevenue()!=null){
                grossMargin = grossMargin.add(calculateMargin(balanceSheet.getGrossProfit(), balanceSheet.getNetRevenue(), scale));
            }
            if(balanceSheet.getNetProfit()!=null && balanceSheet.getNetRevenue()!=null){
                netMargin = netMargin.add(calculateMargin(balanceSheet.getNetProfit(), balanceSheet.getNetRevenue(), scale));
            }
        }

        lastTwelveMonthsResults =  new YearlyBalanceSheet(
                lastFour[0].getAssets(),
                lastFour[0].getCash(),
                costs,
                ebit,
                ebitda,
                lastFour[0].getEquity(),
                lastFour[0].getGrossDebt(),
                grossProfit,
                lastFour[0].getLiabilities(),
                lastFour[0].getNetDebt(),
                netProfit,
                netRevenue,
                taxes,
                lastFour[0].getYear()
        );
        return lastTwelveMonthsResults;
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
            BigDecimal hundred = new BigDecimal(100);
            List<YearlyValuesResponse> list = responseMap.getOrDefault(itemName, new ArrayList<>());
            list.removeIf(y -> y.getYear() == year);  // Remove existing entry for the same year if it exists
            list.add(new YearlyValuesResponse(average.multiply(hundred), year));
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
