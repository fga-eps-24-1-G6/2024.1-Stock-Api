package com.stocksapi.service;

import com.stocksapi.dto.*;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.BalanceSheet;
import com.stocksapi.model.Dividends;
import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import com.stocksapi.dto.SearchedStocksResponse;
import com.stocksapi.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StocksService {

    private final StockRepository stockRepository;
    private final PriceRepository priceRepository;
    private final BalanceSheetsRepository balanceSheetsRepository;
    private final DividendsRepository dividendsRepository;
    private final CompaniesRepository companiesRepository;

    public StocksService(StockRepository stockRepository, PriceRepository priceRepository, BalanceSheetsRepository balanceSheetsRepository, DividendsRepository dividendsRepository, CompaniesRepository companiesRepository) {
        this.stockRepository = stockRepository;
        this.priceRepository = priceRepository;
        this.balanceSheetsRepository = balanceSheetsRepository;
        this.dividendsRepository = dividendsRepository;
        this.companiesRepository = companiesRepository;
    }

    public StocksResponse getStocksByTicker (String ticker) {
        Stocks stocks = stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find stocks with ticker: " + ticker));

        Prices prices = priceRepository.findLatestPriceByStockId(stocks.getId())
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find prices with ticker: " + ticker));

        String categorie = "LARGE";
        BigDecimal tenBillion = new BigDecimal("10000000000");
        int comnpareTo = tenBillion.compareTo(stocks.getCompanies().getFirmValue());
        if (comnpareTo < 0) {
            categorie = "SMALL";
        }
        List<Prices> findAllPrices = priceRepository.findAllByStockIdIdOrderByPriceDate(stocks.getId());

        BigDecimal currentPrice = findAllPrices.get(findAllPrices.size() - 1).getValue();
        BigDecimal priceOneDayAgo = getPriceXDaysAgo(findAllPrices, 1);
        BigDecimal variationOneDay = calculateVariation(currentPrice, priceOneDayAgo);

        BigDecimal priceOneMonthAgo = getPriceXMonthsAgo(findAllPrices, 1);
        BigDecimal variationOneMonth = calculateVariation(currentPrice, priceOneMonthAgo);


        BigDecimal priceTwelveMonthsAgo = getPriceXMonthsAgo(findAllPrices, 12);
        BigDecimal variationTwelveMonths = calculateVariation(currentPrice, priceTwelveMonthsAgo);

        return new StocksResponse(stocks, prices, categorie, variationOneDay, variationOneMonth, variationTwelveMonths, stocks.getCompanies().getName());
    }

    public BigDecimal getCeilingPrice(Integer stockId){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusMonths(12);
        List<Dividends> dividendsList = dividendsRepository.findLastTwelveMonthsDividendsByStockId(stockId, startDate, endDate);

        if (!dividendsList.isEmpty()){

            BigDecimal total = dividendsList.stream()
                    .map(dividends -> dividends.getValue())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal ceilingPrice = total.divide(BigDecimal.valueOf(0.06), 2, BigDecimal.ROUND_HALF_UP);

            return ceilingPrice;

        } else{
            throw new BadRequestNotFoundException(404, "Could not find dividends with stockId: " + stockId);

        }

    }

    public IndicatorsResponse getIndicatorsFromStocksByTicker(String ticker) {
        Stocks stocks = stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find stocks with ticker: " + ticker));

        Prices prices = priceRepository.findLatestPriceByStockId(stocks.getId())
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find prices with ticker: " + ticker));

        BalanceSheet balanceSheet = balanceSheetsRepository.findLatestByCompanyId(stocks.getCompanies().getId())
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find balances sheet with ticker: " + ticker));

        List<BalanceSheet> findAllBalances = balanceSheetsRepository.findAllByCompaniesId(stocks.getCompanies().getId());

        List<IndicatorValueResponse> indicators = new ArrayList<>();

        int scale = 10;
        RoundingMode roundingMode = RoundingMode.HALF_UP;

        BigDecimal value = prices.getValue();
        BigDecimal netProfit = balanceSheet.getNetProfit();
        BigDecimal equity = balanceSheet.getEquity();
        BigDecimal netRevenue = balanceSheet.getNetRevenue();
        BigDecimal ebit = balanceSheet.getEbit();
        BigDecimal ebitda = balanceSheet.getEbitda();
        BigDecimal assets = balanceSheet.getAssets();
        BigDecimal liabilities = balanceSheet.getLiabilities();
        BigDecimal netDebt = balanceSheet.getNetDebt();
        BigDecimal numberOfPapers = stocks.getCompanies().getNumberOfPapers();

        BigDecimal lpaValue = netProfit.divide(numberOfPapers, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("LPA", lpaValue));

        BigDecimal plValue = value.divide(lpaValue, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("P/L", plValue));

        BigDecimal vpaValue = equity.divide(numberOfPapers, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("VPA", vpaValue));

        BigDecimal pvpValue = value.divide(vpaValue, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("P/VP", pvpValue));

        BigDecimal divYieldValue = BigDecimal.ZERO;
        BigDecimal payoutValue = BigDecimal.ZERO;
        List<Dividends> dividends = dividendsRepository.findByStocksId(stocks.getId());
        if (!dividends.isEmpty()) {
            BigDecimal dividendValue = dividends.get(0).getValue();
            divYieldValue = dividendValue.divide(value, scale, roundingMode);
            payoutValue = dividendValue.divide(netProfit, scale, roundingMode);
        }
        indicators.add(new IndicatorValueResponse("DIV YIELD", divYieldValue));
        indicators.add(new IndicatorValueResponse("PAYOUT", payoutValue));

        BigDecimal netMarginValue = netProfit.divide(netRevenue, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("MARGEM LÍQ", netMarginValue));

        BigDecimal grossMarginValue = balanceSheet.getGrossProfit().divide(netRevenue, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("MARGEM BRUTA", grossMarginValue));

        BigDecimal ebitMarginValue = ebit.divide(netRevenue, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("MARGEM EBIT", ebitMarginValue));

        BigDecimal marketValue = value.multiply(numberOfPapers);

        BigDecimal evEbitValue = marketValue.divide(ebit, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("EV/EBIT", evEbitValue));

        BigDecimal evEbitdaValue = marketValue.divide(ebitda, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("EV/EBITDA", evEbitdaValue));

        BigDecimal roeValue = netProfit.divide(equity, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("ROE", roeValue));

        BigDecimal roicValue = ebit.divide(liabilities, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("ROIC", roicValue));

        BigDecimal roaValue = netProfit.divide(assets, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("ROA", roaValue));

        BigDecimal divLiqPatLiqValue = netDebt.divide(equity, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("DÍV LÍQ/PAT LÍQ", divLiqPatLiqValue));

        BigDecimal divLiqEbit = netDebt.divide(ebit, scale, roundingMode);
        indicators.add(new IndicatorValueResponse("DÍV LÍQ/EBIT", divLiqEbit));

        BigDecimal profitCagrValue = calculateProfitCAGR(findAllBalances);
        indicators.add(new IndicatorValueResponse("CAGR LUCRO", profitCagrValue));

        BigDecimal cagrRecValue = calculateRevenueCAGR(findAllBalances);
        indicators.add(new IndicatorValueResponse("CAGR REC", cagrRecValue));

        return new IndicatorsResponse(indicators);
    }

    private static BigDecimal calculateVariation(BigDecimal current, BigDecimal previous) {
        return current.subtract(previous)
                .divide(previous, 4, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(100));
    }

    private static BigDecimal getPriceXDaysAgo(List<Prices> prices, int days) {
        LocalDate targetDate = LocalDate.now().minusDays(days);
        return getPriceAtDate(prices, targetDate);
    }

    public static BigDecimal getPriceXMonthsAgo(List<Prices> prices, int months) {
        LocalDate targetDate = LocalDate.now().minusMonths(months);
        return getPriceAtDate(prices, targetDate);
    }

    public static BigDecimal getPriceAtDate(List<Prices> prices, LocalDate targetDate) {
        Optional<Prices> priceAtDate = prices.stream()
                .filter(price -> price.getPriceDate().isBefore(targetDate) || price.getPriceDate().isEqual(targetDate))
                .max(Comparator.comparing(Prices::getPriceDate));

        return priceAtDate.map(Prices::getValue).orElse(BigDecimal.ZERO);
    }

    public static BigDecimal calculateProfitCAGR(List<BalanceSheet> balances) {
        BigDecimal last12MonthsProfit = BigDecimal.ZERO;
        BigDecimal last5YearsProfit = BigDecimal.ZERO;
        int numberOfYears = 5;

        for (BalanceSheet balance : balances) {
            last12MonthsProfit = last12MonthsProfit.add(balance.getNetProfit());
        }

        for (int i = 0; i < numberOfYears; i++) {
            last5YearsProfit = last5YearsProfit.add(balances.get(i).getNetProfit());
        }

        return calculateCAGR(last5YearsProfit, last12MonthsProfit, numberOfYears);
    }

    public static BigDecimal calculateRevenueCAGR(List<BalanceSheet> balances) {
        BigDecimal last12MonthsRevenue = BigDecimal.ZERO;
        BigDecimal last5YearsRevenue = BigDecimal.ZERO;
        int numberOfYears = 5;

        for (BalanceSheet balance : balances) {
            last12MonthsRevenue = last12MonthsRevenue.add(balance.getNetRevenue());
        }

        for (int i = 0; i < numberOfYears; i++) {
            last5YearsRevenue = last5YearsRevenue.add(balances.get(i).getNetRevenue());
        }

        return calculateCAGR(last5YearsRevenue, last12MonthsRevenue, numberOfYears);
    }

    private static BigDecimal calculateCAGR(BigDecimal initialValue, BigDecimal finalValue, int periods) {
        BigDecimal base = finalValue.divide(initialValue, 10, RoundingMode.HALF_UP);
        BigDecimal exponent = BigDecimal.ONE.divide(new BigDecimal(periods), 10, RoundingMode.HALF_UP);
        MathContext mc = new MathContext(10);
        BigDecimal result = BigDecimal.valueOf(Math.exp(exponent.doubleValue() * Math.log(base.doubleValue()))).round(mc);

        return result.subtract(BigDecimal.ONE);
    }
}
