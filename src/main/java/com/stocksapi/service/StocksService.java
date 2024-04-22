package com.stocksapi.service;

import com.stocksapi.dto.StocksResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import com.stocksapi.repository.PriceRepository;
import com.stocksapi.repository.StockRepository;
import org.springframework.stereotype.Service;

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
            Optional<Prices> optPrices = priceRepository.findByStocksId(optStocks.get().getId());
            StocksResponse stocksResponse = new StocksResponse(optStocks.get(), optPrices.get());
            return stocksResponse;
        }
        throw new BadRequestNotFoundException("Could not find stocks with ticker " + ticker);
    }

}
