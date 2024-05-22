package com.stocksapi.service;

import com.stocksapi.dto.DividendsWithDividendYieldResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.Dividends;
import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import com.stocksapi.repository.DividendsRepository;
import com.stocksapi.repository.PriceRepository;
import com.stocksapi.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DividendsService {

    private final DividendsRepository dividendsRepository;
    private final StockRepository stockRepository;
    private final PriceRepository priceRepository;

    public DividendsService(DividendsRepository dividendsRepository, StockRepository stockRepository, PriceRepository priceRepository) {
        this.dividendsRepository = dividendsRepository;
        this.stockRepository = stockRepository;
        this.priceRepository = priceRepository;
    }

    public DividendsWithDividendYieldResponse getDividendsByTicker(String ticker) {
        Stocks stocks = stockRepository.findByTicker(ticker)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find stocks with ticker: " + ticker));

        List<Dividends> dividends = dividendsRepository.findByStocksId(stocks.getId());
        if (dividends.isEmpty()) {
            throw new BadRequestNotFoundException(404, "Could not find dividends for stocks with id: " + stocks.getId());
        }

        Map<Integer, BigDecimal> totalDividendsByYear = dividends.stream()
                .collect(Collectors.groupingBy(
                        dividend -> dividend.getPaymentDate().getYear(),
                        Collectors.mapping(Dividends::getValue, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, BigDecimal>comparingByKey().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        // Mapa para armazenar a contagem de dividendos pagos em cada mês
        Map<String, Integer> dividendCountByMonth = new HashMap<>();

        // Contagem de dividendos pagos em cada mês
        for (Dividends dividend : dividends) {
            String month = dividend.getPaymentDate().getMonth().toString();
            dividendCountByMonth.put(month, dividendCountByMonth.getOrDefault(month, 0) + 1);
        }

        // Total de dividendos
        int totalDividends = dividends.size();

        // Calcular a porcentagem de dividendos pagos em cada mês
        Map<String, String> paymentMonths = new HashMap<>();
        dividendCountByMonth.forEach((month, count) -> {
            double percentage = ((double) count / totalDividends) * 100;
            paymentMonths.put(month, String.valueOf(percentage) + "%");
        });


        Prices latestPrice = priceRepository.findLatestPriceByStockId(stocks.getId())
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find latest price for stocks with ticker: " + ticker));

        // Calculate the Dividend Yields
        BigDecimal dividendYieldCurrent = calculateDividendYieldPerPeriod(totalDividendsByYear, 1, latestPrice.getValue());
        BigDecimal dividendYieldLastFiveYears = calculateDividendYieldPerPeriod(totalDividendsByYear, 5, latestPrice.getValue());
        BigDecimal dividendYieldLastTenYears = calculateDividendYieldPerPeriod(totalDividendsByYear, 10, latestPrice.getValue());

        Map<String, BigDecimal> dividendYield = new HashMap<>();
        dividendYield.put("dividendYieldCurrent", dividendYieldCurrent);
        dividendYield.put("dividendYieldLastFiveYears", dividendYieldLastFiveYears);
        dividendYield.put("dividendYieldLastTenYears", dividendYieldLastTenYears);

        return new DividendsWithDividendYieldResponse(dividends, totalDividendsByYear, paymentMonths, dividendYield);
    }

    public static String getMonthName(LocalDate date) {
        String monthName = date.format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH));
        return monthName;
    }

    public static BigDecimal calculateDividendYieldPerPeriod(Map<Integer, BigDecimal> totalDividendsByYear, Integer count, BigDecimal latestPrice) {
        BigDecimal sum = BigDecimal.ZERO;
        int i = 0;
        for (Map.Entry<Integer, BigDecimal> entry : totalDividendsByYear.entrySet()) {
            sum = sum.add(entry.getValue());
            i++;
            if (i == count) {
                break;
            }
        }
        BigDecimal average = sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        return average.divide(latestPrice, 2, RoundingMode.HALF_UP);
    }
}
