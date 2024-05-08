package com.stocksapi.service;

import com.stocksapi.dto.*;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.BalanceSheet;
import com.stocksapi.model.Dividends;
import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import com.stocksapi.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ValuationResponse getValuationByTicker(String ticker) {
        Optional<Stocks> optStocks = stockRepository.findByTicker(ticker);
        if (optStocks.isPresent()) {

            Integer companyId = optStocks.get().getCompanies().getId();
            BigDecimal result = getLPA(companyId).multiply(getVPA(companyId)).multiply(BigDecimal.valueOf(22.5));
            BigDecimal ceilingPrice = getCeilingPrice(optStocks.get().getId());
            TargetPriceResponse targetPriceResponse = new TargetPriceResponse(Math.sqrt(result.doubleValue()), "PREÇO ALVO" );
            CeilingPriceResponse ceilingPriceResponse = new CeilingPriceResponse(ceilingPrice, "PREÇO TETO");
            ValuationResponse valuationResponse = new ValuationResponse(targetPriceResponse, ceilingPriceResponse);
            return valuationResponse;
        }
        throw new BadRequestNotFoundException(404, "Could not find stocks with ticker " + ticker);

    }

    private BigDecimal getLPA(int companyId) { //metodo auxiliar
        BigDecimal numberOfPapers;
        BigDecimal netProfit;
        BalanceSheet latestBalanceSheet = balanceSheetsRepository.findLatestBalanceSheetByCompanyId(companyId)[0];
        netProfit = latestBalanceSheet.getNetProfit();
        numberOfPapers = companiesRepository.findById(companyId).get().getNumberOfPapers();
        return netProfit.divide(numberOfPapers, 2, BigDecimal.ROUND_HALF_UP);

    }

    private BigDecimal getPL(Integer stockId, Integer companyId) {
            List<Prices> findAllPrices = priceRepository.findAllByStockIdIdOrderByPriceDateDesc(stockId);
            BigDecimal currentPrice = findAllPrices.get(findAllPrices.size() - 1).getValue();
            return currentPrice.divide(getLPA(companyId), 2,BigDecimal.ROUND_HALF_UP) ;

    }

    private BigDecimal getVPA(int companyId) { //metodo auxiliar
        BigDecimal numberOfPapers;
        BigDecimal equity;
        BalanceSheet latestBalanceSheet = balanceSheetsRepository.findLatestBalanceSheetByCompanyId(companyId)[0];
        equity= latestBalanceSheet.getEquity();
        numberOfPapers = companiesRepository.findById(companyId).get().getNumberOfPapers();
        return equity.divide(numberOfPapers, 2, BigDecimal.ROUND_HALF_UP);

    }


    public StocksResponse getStocksByTicker(String ticker) {
        Optional<Stocks> optStocks = stockRepository.findByTicker(ticker);
        if (optStocks.isPresent()) {
            Optional<Prices> optPrices = priceRepository.findLatestPriceByStockId(optStocks.get().getId());
            String categorie = "LARGE";
            BigDecimal tenBillion = new BigDecimal("10000000000");
            int comnpareTo = tenBillion.compareTo(optStocks.get().getCompanies().getFirmValue());
            if (comnpareTo < 0) {
                categorie = "SMALL";
            }
            List<Prices> findAllPrices = priceRepository.findAllByStockIdIdOrderByPriceDateDesc(optStocks.get().getId());

            BigDecimal currentPrice = findAllPrices.get(findAllPrices.size() - 1).getValue();
            BigDecimal priceOneDayAgo = getPriceXDaysAgo(findAllPrices, 1);
            BigDecimal variationOneDay = calculateVariation(currentPrice, priceOneDayAgo);

            BigDecimal priceOneMonthAgo = getPriceXMonthsAgo(findAllPrices, 1);
            BigDecimal variationOneMonth = calculateVariation(currentPrice, priceOneMonthAgo);


            BigDecimal priceTwelveMonthsAgo = getPriceXMonthsAgo(findAllPrices, 12);
            BigDecimal variationTwelveMonths = calculateVariation(currentPrice, priceTwelveMonthsAgo);

            StocksResponse stocksResponse = new StocksResponse(optStocks.get(), optPrices.get(), categorie, variationOneDay, variationOneMonth, variationTwelveMonths, optStocks.get().getCompanies().getName());
            return stocksResponse;
        }
        throw new BadRequestNotFoundException(404, "Could not find stocks with ticker " + ticker);
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
        Optional<Stocks> optStocks = stockRepository.findByTicker(ticker);
        List<IndicatorValueResponse> indicators = new ArrayList<IndicatorValueResponse>();
        if (optStocks.isPresent()) {
            Optional<Prices> optPrices = priceRepository.findLatestPriceByStockId(optStocks.get().getId());

            if (optPrices.isPresent()) {
                Optional<BalanceSheet[]> optBalanceSheets = balanceSheetsRepository.findLatestByCompanyId(optStocks.get().getCompanies().getId());
                if (optBalanceSheets.isPresent()) {
                    BalanceSheet latestBalanceSheet = optBalanceSheets.get()[0];
                    int scale = 10;
                    RoundingMode roundingMode = RoundingMode.HALF_UP;
                    BigDecimal value = optPrices.get().getValue();
                    BigDecimal netProfit = latestBalanceSheet.getNetProfit();
                    BigDecimal equity = latestBalanceSheet.getEquity();

                    BigDecimal lpaValue = netProfit.divide(optStocks.get().getCompanies().getNumberOfPapers(), scale, roundingMode);
                    BigDecimal plValue = value.divide(lpaValue, scale, roundingMode);
                    IndicatorValueResponse plResponse = new IndicatorValueResponse("P/L", plValue);
                    indicators.add(plResponse);

                    BigDecimal vpaValue = equity.divide(optStocks.get().getCompanies().getNumberOfPapers(), scale, roundingMode);
                    BigDecimal pvpValue = value.divide(vpaValue, scale, roundingMode);
                    IndicatorValueResponse pvpResponse = new IndicatorValueResponse("P/VP", pvpValue);
                    indicators.add(pvpResponse);

                    // Rever questao de calculo de valores de proventos, Batista explicou certinho
                    Optional<Dividends> optDividends = dividendsRepository.findByStocksId(optStocks.get().getId());
                    BigDecimal divYieldValue = optDividends.get().getValue().divide(value, scale, roundingMode);
                    IndicatorValueResponse divYieldResponse = new IndicatorValueResponse("DIV YIELD", divYieldValue);
                    indicators.add(divYieldResponse);

                    BigDecimal payoutValue = optDividends.get().getValue().divide(netProfit, scale, roundingMode);
                    IndicatorValueResponse payoutResponse = new IndicatorValueResponse("PAYOUT", payoutValue);
                    indicators.add(payoutResponse);

                    BigDecimal netMarginValue = latestBalanceSheet.getNetProfit().divide(latestBalanceSheet.getNetRevenue(), scale, roundingMode);
                    IndicatorValueResponse netMarginResponse = new IndicatorValueResponse("MARGEM LÍQ", netMarginValue);
                    indicators.add(netMarginResponse);

                    BigDecimal grossMarginValue = latestBalanceSheet.getGrossProfit().divide(latestBalanceSheet.getNetRevenue(), scale, roundingMode);
                    IndicatorValueResponse grossMarginResponse = new IndicatorValueResponse("MARGEM BRUTA", grossMarginValue);
                    indicators.add(grossMarginResponse);

                    BigDecimal ebitMarginValue = latestBalanceSheet.getEbit().divide(latestBalanceSheet.getNetRevenue(), scale, roundingMode);
                    IndicatorValueResponse ebitMarginResponse = new IndicatorValueResponse("MARGEM EBIT", ebitMarginValue);
                    indicators.add(ebitMarginResponse);

                    BigDecimal marketValue = optPrices.get().getValue().multiply(optStocks.get().getCompanies().getNumberOfPapers());

                    BigDecimal evEbitValue = marketValue.divide(latestBalanceSheet.getEbit(), scale, roundingMode);
                    IndicatorValueResponse evEbitResponse = new IndicatorValueResponse("EV/EBIT", evEbitValue);
                    indicators.add(evEbitResponse);

                    BigDecimal evEbitdaValue = marketValue.divide(latestBalanceSheet.getEbitda(), scale, roundingMode);
                    IndicatorValueResponse evEbitdaResponse = new IndicatorValueResponse("EV/EBITDA", evEbitdaValue);
                    indicators.add(evEbitdaResponse);

                    IndicatorValueResponse vpaResponse = new IndicatorValueResponse("VPA", vpaValue);
                    indicators.add(vpaResponse);

                    IndicatorValueResponse lpaResponse = new IndicatorValueResponse("LPA", lpaValue);
                    indicators.add(lpaResponse);

                    BigDecimal roeValue = latestBalanceSheet.getNetProfit().divide(latestBalanceSheet.getEquity(), scale, roundingMode);
                    IndicatorValueResponse roeResponse = new IndicatorValueResponse("ROE", roeValue);
                    indicators.add(roeResponse);

                    BigDecimal roicValue = latestBalanceSheet.getEbit().divide(latestBalanceSheet.getLiabilities(), scale, roundingMode);
                    IndicatorValueResponse roicResponse = new IndicatorValueResponse("ROIC", roicValue);
                    indicators.add(roicResponse);

                    BigDecimal roaValue = latestBalanceSheet.getNetProfit().divide(latestBalanceSheet.getAssets(), scale, roundingMode);
                    IndicatorValueResponse roaResponse = new IndicatorValueResponse("ROA", roaValue);
                    indicators.add(roaResponse);

                    // Rever lógica de cálculo de CAGR LUCRO e CAGR REC
                    BigDecimal profitCagrValue = latestBalanceSheet.getNetProfit().multiply(latestBalanceSheet.getNetProfit());
                    IndicatorValueResponse profitCagrResponse = new IndicatorValueResponse("CAGR LUCRO", profitCagrValue);
                    indicators.add(profitCagrResponse);

                    BigDecimal cagrRecValue = latestBalanceSheet.getNetRevenue().multiply(latestBalanceSheet.getNetRevenue());
                    IndicatorValueResponse cagrRecResponse = new IndicatorValueResponse("CAGR REC", cagrRecValue);
                    indicators.add(cagrRecResponse);

                    BigDecimal divLiqPatLiqValue = latestBalanceSheet.getNetDebt().divide(latestBalanceSheet.getEquity(), scale, roundingMode);
                    IndicatorValueResponse divLiqPatLiqResponse = new IndicatorValueResponse("DÍV LÍQ/PAT LÍQ", divLiqPatLiqValue);
                    indicators.add(divLiqPatLiqResponse);

                    BigDecimal divLiqEbit = latestBalanceSheet.getNetDebt().divide(latestBalanceSheet.getEbit(), scale, roundingMode);
                    IndicatorValueResponse divLiqEbitResponse = new IndicatorValueResponse("DÍV LÍQ/EBIT", divLiqEbit);
                    indicators.add(divLiqEbitResponse);

                    return new IndicatorsResponse(indicators);

                }

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
        LocalDate targetDate = prices.get(prices.size() - 1).getPriceDate().minusDays(days);
        return getPriceAtDate(prices, targetDate);
    }

    private static BigDecimal getPriceXMonthsAgo(List<Prices> prices, int months) {
        LocalDate targetDate = prices.get(prices.size() - 1).getPriceDate().minusMonths(months);
        return getPriceAtDate(prices, targetDate);
    }

    private static BigDecimal getPriceAtDate(List<Prices> prices, LocalDate targetDate) {
        for (int i = prices.size() - 1; i >= 0; i--) {
            Prices price = prices.get(i);
            if (price.getPriceDate().isBefore(targetDate) || price.getPriceDate().isEqual(targetDate)) {
                return price.getValue();
            }
        }
        return null;
    }

    public List<SearchedStocksResponse> searchStocks(String ticker, String companyName, String category, String sector) {
        List<SearchedStocksResponse> responseList = new ArrayList<SearchedStocksResponse>();
        if ("small".equalsIgnoreCase(category)) {
            List<Stocks> stocksList = stockRepository.searchSmall(companyName, sector, ticker);
            List<Prices> pricesList = priceRepository.findAllByStockIdIdOrderByPriceDateDesc(stocksList.get(0).getId());
            if (!pricesList.isEmpty()){
                if (!stocksList.isEmpty()){
                    for (Stocks stock : stocksList){
                        BigDecimal variationOneDay = pricesList.get(0).getValue().subtract(pricesList.get(1).getValue()).abs();
                        SearchedStocksResponse searchedStocksResponse = new SearchedStocksResponse(stock.getCompanies().getId(), stock.getCompanies().getName(), stock.getId(), pricesList.get(0).getValue(), stock.getTicker(), variationOneDay);
                        responseList.add(searchedStocksResponse);
                    }
                } else {
                    throw new BadRequestNotFoundException(404, "Could not find stocks with ticker " + ticker + " or " + sector +  " or " + companyName + " or " + category);
                }
            } else {
                throw new BadRequestNotFoundException(404, "Could not find prices with stockId " + stocksList.get(0).getId());
            }
            return responseList;
        } else {
            List<Stocks> stocksList = stockRepository.searchLarge(companyName, sector, ticker);
            List<Prices> pricesList = priceRepository.findAllByStockIdIdOrderByPriceDateDesc(stocksList.get(0).getId());
            if (!pricesList.isEmpty()){
                if (!stocksList.isEmpty()){
                    for (Stocks stock : stocksList){
                        BigDecimal variationOneDay = pricesList.get(0).getValue().subtract(pricesList.get(1).getValue()).abs();
                        SearchedStocksResponse searchedStocksResponse = new SearchedStocksResponse(stock.getCompanies().getId(), stock.getCompanies().getName(), stock.getId(), pricesList.get(0).getValue(), stock.getTicker(), variationOneDay);
                        responseList.add(searchedStocksResponse);
                    }
                } else {
                    throw new BadRequestNotFoundException(404, "Could not find stocks with ticker " + ticker + " or " + sector +  " or " + companyName + " or " + category);
                }
            } else {
                throw new BadRequestNotFoundException(404, "Could not find prices with stockId " + stocksList.get(0).getId());
            }
            return responseList;
        }
    }
}
