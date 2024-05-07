package com.stocksapi.dto;

import com.stocksapi.model.Companies;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
public class CompaniesResponse {
    private Integer ipo;
    private String sector;
    private String segment;
    private BigDecimal marketValue;
    private BigDecimal equity;
    private BigDecimal numberOfPapers;

    public CompaniesResponse(Companies companies, BigDecimal marketValue, BigDecimal equity) {
        this.ipo = companies.getIpo();
        this.sector = companies.getSector();
        this.segment = companies.getSegment();
        this.marketValue = marketValue;
        this.equity = equity;
        this.numberOfPapers = companies.getNumberOfPapers();
    }
}
