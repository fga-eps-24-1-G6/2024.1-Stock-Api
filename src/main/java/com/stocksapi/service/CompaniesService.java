package com.stocksapi.service;

import com.stocksapi.dto.CompaniesResponse;
import com.stocksapi.dto.StocksResponse;
import com.stocksapi.exception.BadRequestNotFoundException;
import com.stocksapi.model.BalanceSheet;
import com.stocksapi.model.Companies;
import com.stocksapi.model.Prices;
import com.stocksapi.model.Stocks;
import com.stocksapi.repository.BalanceSheetsRepository;
import com.stocksapi.repository.CompaniesRepository;
import com.stocksapi.repository.PriceRepository;
import com.stocksapi.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CompaniesService {

    private final CompaniesRepository companiesRepository;
    private final BalanceSheetsRepository balanceSheetsRepository;
    private final PriceRepository priceRepository;
    private final StockRepository stockRepository;

    public CompaniesService(CompaniesRepository companiesRepository, BalanceSheetsRepository balanceSheetsRepository, PriceRepository priceRepository, StockRepository stockRepository) {
        this.companiesRepository = companiesRepository;
        this.balanceSheetsRepository = balanceSheetsRepository;
        this.priceRepository = priceRepository;
        this.stockRepository = stockRepository;
    }

    public CompaniesResponse getById(Integer id) {
        Optional<Companies> optCompanies = companiesRepository.findById(id);
        if (optCompanies.isPresent()) {
            Optional<Stocks> optStocks = stockRepository.findByCompanyId(id);
            if (optStocks.isPresent()) {
                Optional<Prices> optPrices = priceRepository.findLatestPriceByStockId(optStocks.get().getId());
                BigDecimal numberOfPapers = optCompanies.get().getNumberOfPapers();
                BigDecimal marketValue = optPrices.get().getValue().multiply(numberOfPapers);
                BalanceSheet optBalanceSheet = balanceSheetsRepository.findLatestBalanceSheetByCompanyId(id);
                CompaniesResponse companiesResponse = new CompaniesResponse(optCompanies.get(), marketValue, optBalanceSheet.getEquity());
                return companiesResponse;
            } else {
                throw new BadRequestNotFoundException(404, "Could not find stocks from Company with id: " + id);
            }
        }
        throw new BadRequestNotFoundException(404, "Could not find company with id: " + id);
    }
}
