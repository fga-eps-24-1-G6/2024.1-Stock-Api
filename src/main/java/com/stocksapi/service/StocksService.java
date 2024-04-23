package com.stocksapi.service;

import com.stocksapi.dto.StocksResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import com.stocksapi.repository.PriceRepository;
import com.stocksapi.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StocksService {

    private final StockRepository stockRepository;
    private final PriceRepository priceRepository;

    public StocksService(StockRepository stockRepository, PriceRepository priceRepository) {
        this.stockRepository = stockRepository;
        this.priceRepository = priceRepository;
    }

    public StocksResponse getStocksByTicker (String ticker) {
        Optional<Stocks> optStocks = stockRepository.findByTicker(ticker);
        if (optStocks.isPresent()) {
            Optional<Prices> optPrices = priceRepository.findLatestPriceByStocksId(optStocks.get().getId());
            String categorie = "LARGE";
            BigDecimal tenBillion = new BigDecimal("10000000000");
            int comnpareTo = tenBillion.compareTo(optStocks.get().getCompanies().getFirmValue());
            if (comnpareTo < 0) {
                categorie = "SMALL";
            }
            List<Prices> findAllPrices = priceRepository.findAllByStocksIdOrderByPriceDate(optStocks.get().getId());

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

}
