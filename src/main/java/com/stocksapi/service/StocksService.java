package com.stocksapi.service;

import com.stocksapi.dto.IndicatorValueResponse;
import com.stocksapi.dto.IndicatorsResponse;
import com.stocksapi.dto.StocksResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.BalanceSheet;
import com.stocksapi.model.Dividends;
import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import com.stocksapi.repository.BalanceSheetsRepository;
import com.stocksapi.repository.DividendsRepository;
import com.stocksapi.repository.PriceRepository;
import com.stocksapi.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StocksService {

    private final StockRepository stockRepository;
    private final PriceRepository priceRepository;
    private final BalanceSheetsRepository balanceSheetsRepository;
    private final DividendsRepository dividendsRepository;

    public StocksService(StockRepository stockRepository, PriceRepository priceRepository, BalanceSheetsRepository balanceSheetsRepository, DividendsRepository dividendsRepository) {
        this.stockRepository = stockRepository;
        this.priceRepository = priceRepository;
        this.balanceSheetsRepository = balanceSheetsRepository;
        this.dividendsRepository = dividendsRepository;
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

    public IndicatorsResponse getIndicatorsFromStocksByTicker(String ticker) {
        Optional<Stocks> optStocks = stockRepository.findByTicker(ticker);
        List<IndicatorValueResponse> indicators = new ArrayList<IndicatorValueResponse>();
        if (optStocks.isPresent()) {
            Optional<Prices> optPrices = priceRepository.findLatestPriceByStockId(optStocks.get().getId());

            if (optPrices.isPresent()) {
                Optional<BalanceSheet> optBalanceSheets = balanceSheetsRepository.findLatestByCompanyId(optStocks.get().getCompanies().getId());

                int scale = 10;
                RoundingMode roundingMode = RoundingMode.HALF_UP;
                BigDecimal value = optPrices.get().getValue();
                BigDecimal netProfit = optBalanceSheets.get().getNetProfit();
                BigDecimal equity = optBalanceSheets.get().getEquity();

                BigDecimal lpaValue = netProfit.divide(optStocks.get().getCompanies().getNumberOfPapers(), scale, roundingMode);
                BigDecimal plValue = value.divide(lpaValue, scale, roundingMode);
                IndicatorValueResponse plResponse = new IndicatorValueResponse("P/L", plValue);
                indicators.add(plResponse);

                BigDecimal vpaValue = equity.divide(optStocks.get().getCompanies().getNumberOfPapers(), scale, roundingMode);
                BigDecimal pvpValue = value.divide(vpaValue, scale, roundingMode);
                IndicatorValueResponse pvpResponse = new IndicatorValueResponse("P/VP", pvpValue);
                indicators.add(pvpResponse);

                // Rever questao de calculo de valores de proventos, Batista explicou certinho
//                List<Dividends> optDividends = dividendsRepository.findByStocksId(optStocks.get().getId());
//                BigDecimal divYieldValue = optDividends.get().getValue().divide(value, scale, roundingMode);
//                IndicatorValueResponse divYieldResponse = new IndicatorValueResponse("DIV YIELD", divYieldValue);
//                indicators.add(divYieldResponse);
//
//                BigDecimal payoutValue = optDividends.get().getValue().divide(netProfit, scale, roundingMode);
//                IndicatorValueResponse payoutResponse = new IndicatorValueResponse("PAYOUT", payoutValue);
//                indicators.add(payoutResponse);
//
//                BigDecimal netMarginValue = optBalanceSheets.get().getNetProfit().divide(optBalanceSheets.get().getNetRevenue(), scale, roundingMode);
//                IndicatorValueResponse netMarginResponse = new IndicatorValueResponse("MARGEM LÍQ", netMarginValue);
//                indicators.add(netMarginResponse);
//
//                BigDecimal grossMarginValue = optBalanceSheets.get().getGrossProfit().divide(optBalanceSheets.get().getNetRevenue(), scale, roundingMode);
//                IndicatorValueResponse grossMarginResponse = new IndicatorValueResponse("MARGEM BRUTA", grossMarginValue);
//                indicators.add(grossMarginResponse);
//
//                BigDecimal ebitMarginValue = optBalanceSheets.get().getEbit().divide(optBalanceSheets.get().getNetRevenue(), scale, roundingMode);
//                IndicatorValueResponse ebitMarginResponse = new IndicatorValueResponse("MARGEM EBIT", ebitMarginValue);
//                indicators.add(ebitMarginResponse);
//
//                BigDecimal marketValue = optPrices.get().getValue().multiply(optStocks.get().getCompanies().getNumberOfPapers());
//
//                BigDecimal evEbitValue = marketValue.divide(optBalanceSheets.get().getEbit(), scale, roundingMode);
//                IndicatorValueResponse evEbitResponse = new IndicatorValueResponse("EV/EBIT", evEbitValue);
//                indicators.add(evEbitResponse);
//
//                BigDecimal evEbitdaValue = marketValue.divide(optBalanceSheets.get().getEbitda(), scale, roundingMode);
//                IndicatorValueResponse evEbitdaResponse = new IndicatorValueResponse("EV/EBITDA", evEbitdaValue);
//                indicators.add(evEbitdaResponse);
//
//                IndicatorValueResponse vpaResponse = new IndicatorValueResponse("VPA", vpaValue);
//                indicators.add(vpaResponse);
//
//                IndicatorValueResponse lpaResponse = new IndicatorValueResponse("LPA", lpaValue);
//                indicators.add(lpaResponse);
//
//                BigDecimal roeValue = optBalanceSheets.get().getNetProfit().divide(optBalanceSheets.get().getEquity(), scale, roundingMode);
//                IndicatorValueResponse roeResponse = new IndicatorValueResponse("ROE", roeValue);
//                indicators.add(roeResponse);
//
//                BigDecimal roicValue = optBalanceSheets.get().getEbit().divide(optBalanceSheets.get().getLiabilities(), scale, roundingMode);
//                IndicatorValueResponse roicResponse = new IndicatorValueResponse("ROIC", roicValue);
//                indicators.add(roicResponse);
//
//                BigDecimal roaValue = optBalanceSheets.get().getNetProfit().divide(optBalanceSheets.get().getAssets(), scale, roundingMode);
//                IndicatorValueResponse roaResponse = new IndicatorValueResponse("ROA", roaValue);
//                indicators.add(roaResponse);
//
//                // Rever lógica de cálculo de CAGR LUCRO e CAGR REC
//                BigDecimal profitCagrValue = optBalanceSheets.get().getNetProfit().multiply(optBalanceSheets.get().getNetProfit());
//                IndicatorValueResponse profitCagrResponse = new IndicatorValueResponse("CAGR LUCRO", profitCagrValue);
//                indicators.add(profitCagrResponse);
//
//                BigDecimal cagrRecValue = optBalanceSheets.get().getNetRevenue().multiply(optBalanceSheets.get().getNetRevenue());
//                IndicatorValueResponse cagrRecResponse = new IndicatorValueResponse("CAGR REC", cagrRecValue);
//                indicators.add(cagrRecResponse);
//
//                BigDecimal divLiqPatLiqValue = optBalanceSheets.get().getNetDebt().divide(optBalanceSheets.get().getEquity(), scale, roundingMode);
//                IndicatorValueResponse divLiqPatLiqResponse = new IndicatorValueResponse("DÍV LÍQ/PAT LÍQ", divLiqPatLiqValue);
//                indicators.add(divLiqPatLiqResponse);
//
//                BigDecimal divLiqEbit = optBalanceSheets.get().getNetDebt().divide(optBalanceSheets.get().getEbit(), scale, roundingMode);
//                IndicatorValueResponse divLiqEbitResponse = new IndicatorValueResponse("DÍV LÍQ/EBIT", divLiqEbit);
//                indicators.add(divLiqEbitResponse);

                return new IndicatorsResponse(indicators);

            } else {
                throw new BadRequestNotFoundException(404, "Could not find stocks with ticker " + ticker);
            }

        }

        throw new BadRequestNotFoundException(404, "Could not find stocks with ticker " + ticker);
    }

    private static BigDecimal calculateVariation(BigDecimal current, BigDecimal previous) {
        return current.subtract(previous)
                .divide(previous, 4, BigDecimal.ROUND_HALF_EVEN)
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

}
