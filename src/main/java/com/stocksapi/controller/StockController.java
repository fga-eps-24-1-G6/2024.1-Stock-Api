package com.stocksapi.controller;

import com.stocksapi.dto.ExceptionResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.service.StocksService;
import org.springframework.http.HttpStatus;
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
        } catch (BadRequestNotFoundException exception) {
            int errorCode = 404;
            String message = "Could not find stocks with ticker: " + ticker;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(errorCode, message));
        }
    }
}
