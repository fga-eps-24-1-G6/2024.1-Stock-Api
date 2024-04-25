package com.stocksapi.controller;

import com.stocksapi.dto.ExceptionResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.service.BalanceSheetsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/balance-sheet")
public class BalanceSheetsController {

    private final BalanceSheetsService balanceSheetsService;

    public BalanceSheetsController(BalanceSheetsService balanceSheetsService) {
        this.balanceSheetsService = balanceSheetsService;
    }

    @GetMapping(value = "/{companyId}")
    public ResponseEntity<?> getBalanceSheetsByCompanyId(@PathVariable Integer companyId) {
        try {
            return ResponseEntity.ok(balanceSheetsService.getByCompanyId(companyId));
        } catch (BadRequestNotFoundException exception) {
            int errorCode = 404;
            String message = "Could not Balance Sheets with company id: " + companyId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(errorCode, message));
        }
    }
}
