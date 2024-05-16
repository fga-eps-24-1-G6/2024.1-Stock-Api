package com.stocksapi.controller;

import com.stocksapi.dto.ExceptionResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.service.CompaniesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompaniesService companiesService;

    @GetMapping(value = "/company-info/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(companiesService.getById(id));
        } catch (BadRequestNotFoundException exception) {
            int errorCode = 404;
            String message = "Could not find company with id: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(errorCode, message));
        }
    }
}
