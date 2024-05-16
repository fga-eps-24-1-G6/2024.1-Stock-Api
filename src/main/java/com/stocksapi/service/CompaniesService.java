package com.stocksapi.service;

import com.stocksapi.dto.CompaniesResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.BalanceSheet;
import com.stocksapi.model.Companies;
import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import com.stocksapi.repository.BalanceSheetsRepository;
import com.stocksapi.repository.CompaniesRepository;
import com.stocksapi.repository.PriceRepository;
import com.stocksapi.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CompaniesService {

    private final CompaniesRepository companiesRepository;
    private final BalanceSheetsRepository balanceSheetsRepository;
    private final PriceRepository priceRepository;
    private final StockRepository stockRepository;

    public CompaniesResponse getById(Integer id) {
        Companies companies = companiesRepository.findById(id)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find company with id: " + id));

        Stocks stocks = stockRepository.findByCompanyId(id)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find stocks from Company with id: " + id));

        Prices latestPrice = priceRepository.findLatestPriceByStockId(stocks.getId())
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find latest price for stock with id: " + stocks.getId()));

        BigDecimal marketValue = latestPrice.getValue().multiply(companies.getNumberOfPapers());

        BalanceSheet[] balanceSheet = balanceSheetsRepository.findLatestByCompanyId(id)
                .orElseThrow(() -> new BadRequestNotFoundException(404, "Could not find balance sheet for company with id: " + id));

        return new CompaniesResponse(companies, marketValue, balanceSheet[0].getEquity());
    }
}
