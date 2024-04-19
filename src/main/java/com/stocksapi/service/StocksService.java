package com.stocksapi.service;

import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.Stocks;
import com.stocksapi.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StocksService {

    private final StockRepository stockRepository;

    public StocksService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stocks getStocksByTicker (String ticker) {
        Optional<Stocks> optStocks = stockRepository.findByTicker(ticker);
        return optStocks.orElse(null);
    }

}
