package com.stocksapi.service;

import com.stocksapi.dto.DividendYieldResponse;
import com.stocksapi.dto.DividendsResponse;
import com.stocksapi.dto.DividendsWithDividendYieldResponse;
import com.stocksapi.dto.PaymentMonthResponse;
import com.stocksapi.dto.YearlyPaymentResponse;
import com.stocksapi.dto.YearlyValuesResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
        Optional<Stocks> optStocks = stockRepository.findByTicker(ticker);
        if (optStocks.isPresent()) {
            Optional<Dividends> optDividends = dividendsRepository.findByStocksId(optStocks.get().getId());
            if (optDividends.isPresent()) {
                Optional<Prices> optPrices = priceRepository.findLatestPriceByStockId(optStocks.get().getId());
                List<DividendsResponse> dividendsResponseList = new ArrayList<DividendsResponse>();
                List<PaymentMonthResponse> paymentMonthResponseList = new ArrayList<PaymentMonthResponse>();
                List<YearlyPaymentResponse> yearlyPaymentResponseList = new ArrayList<YearlyPaymentResponse>();

                // Ver logica de calculo com Batista quando tivermos mais registros no banco
                // Rever tudo na verdade :melting_face:
                int scale = 10;
                RoundingMode roundingMode = RoundingMode.HALF_UP;
                BigDecimal value = optPrices.get().getValue();
                BigDecimal divYieldValue = optDividends.get().getValue().divide(value, scale, roundingMode);

                DividendYieldResponse dividendYieldResponse = new DividendYieldResponse(divYieldValue, divYieldValue, divYieldValue);

                String monthName = getMonthName(optDividends.get().getPaymentDate());
                PaymentMonthResponse paymentMonthResponse = new PaymentMonthResponse(optDividends.get().getValue() ,monthName);
                paymentMonthResponseList.add(paymentMonthResponse);

                YearlyPaymentResponse yearlyPaymentResponse = new YearlyPaymentResponse(optDividends.get().getValue(), optDividends.get().getPaymentDate().getYear());
                yearlyPaymentResponseList.add(yearlyPaymentResponse);

                DividendsResponse dividendsResponse = new DividendsResponse(optDividends.get().getOwnershipDate(), optDividends.get().getValue(), optDividends.get().getPaymentDate(), optDividends.get().getType());
                dividendsResponseList.add(dividendsResponse);

                DividendsWithDividendYieldResponse dividendsWithDividendYieldResponse = new DividendsWithDividendYieldResponse(dividendsResponseList, dividendYieldResponse, paymentMonthResponseList, yearlyPaymentResponseList);

               return dividendsWithDividendYieldResponse;
            }
            else {
                throw new BadRequestNotFoundException(404, "Could not find dividends from stocks with ticker: " + ticker);
            }
        } else {
            throw new BadRequestNotFoundException(404, "Could not find dividends from stocks with ticker: " + ticker);
        }

    }

    public static String getMonthName(LocalDate date) {
        String monthName = date.format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH));
        return monthName;
    }
}
