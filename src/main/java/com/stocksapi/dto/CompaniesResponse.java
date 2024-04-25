package com.stocksapi.dto;

import com.stocksapi.model.Companies;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CompaniesResponse {
    private Integer ipo;
    private String sector;
    private String segment;
    private BigDecimal marketValue;
    private BigDecimal equity;
    private Long numberOfPapers;


    public CompaniesResponse(Companies companies, BigDecimal marketValue, BigDecimal equity) {
        this.ipo = companies.getIpo();
        this.sector = companies.getSector();
        this.segment = companies.getSegment();
        this.marketValue = marketValue;
        this.equity = equity;
        this.numberOfPapers = companies.getNumberOfPapers();
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public Integer getIpo() {
        return ipo;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public Long getNumberOfPapers() {
        return numberOfPapers;
    }

    public String getSector() {
        return sector;
    }

    public String getSegment() {
        return segment;
    }
}
