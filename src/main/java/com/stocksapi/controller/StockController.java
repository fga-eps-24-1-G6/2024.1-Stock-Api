package com.stocksapi.controller;

import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.service.StocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StocksService stocksService;

    public StockController(StocksService stocksService) {
        this.stocksService = stocksService;
    }

    @GetMapping(value = "/stock-summary/{ticker}")
    public ResponseEntity<?> getStocksByTicker(@PathVariable String ticker) {
        try {
            return ResponseEntity.ok(stocksService.getStocksByTicker(ticker));
        } catch (BadRequestNotFoundException exception)
        {
            return ResponseEntity.badRequest().body(exception.getMensagem());
        }

    }
}
