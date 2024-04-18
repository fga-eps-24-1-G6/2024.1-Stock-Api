package com.stocksapi.controller;

import com.stocksapi.dto.StocksResponse;
import com.stocksapi.mapper.StocksMapper;
import com.stocksapi.service.StocksService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StocksService stocksService;
    private final StocksMapper stocksMapper;

    public StockController(StocksService stocksService, StocksMapper stocksMapper) {
        this.stocksService = stocksService;
        this.stocksMapper = stocksMapper;
    }

    @GetMapping("/stock-summary/{ticker}")

    public StocksResponse getStocksByTicker(@PathVariable String ticker) {
        return stocksMapper.stocksToStocksResponse(stocksService.getStocksByTicker(ticker));
    }
}
