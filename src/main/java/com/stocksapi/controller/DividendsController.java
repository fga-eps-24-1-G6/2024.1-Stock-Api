package com.stocksapi.controller;

import com.stocksapi.dto.ExceptionResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.service.DividendsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/dividends")
public class DividendsController {

    private final DividendsService dividendsService;

    @GetMapping(value = "/{ticker}")
    public ResponseEntity<?> getDividendsByTicker(@PathVariable String ticker) {
        try {
            return ResponseEntity.ok(dividendsService.getDividendsByTicker(ticker));
        } catch (BadRequestNotFoundException exception) {
            int errorCode = 404;
            String message = "Could not find dividends with ticker: " + ticker;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(errorCode, message));
        }
    }
}
