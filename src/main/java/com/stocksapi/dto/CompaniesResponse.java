package com.stocksapi.dto;

import com.stocksapi.model.Companies;

import java.math.BigDecimal;

public class CompaniesResponse {
    private Integer id;
    private String name;
    private String cnpj;
    private Integer ipo;
    private Integer foundationYear;
    private BigDecimal firmValue;
    private Long numberOfPapers;
    private String marketSegment;
    private String sector;
    private String segment;

    public CompaniesResponse (Companies companies){
        this.id = companies.getId();
        this.name = companies.getName();
        this.cnpj = companies.getCnpj();
        this.ipo = companies.getIpo();
        this.foundationYear = companies.getFoundationYear();
        this.firmValue = companies.getFirmValue();
        this.numberOfPapers = companies.getNumberOfPapers();
        this.marketSegment = companies.getMarketSegment();
        this.sector = companies.getSector();
        this.segment = companies.getSegment();

    }

    public String getCnpj() {
        return cnpj;
    }

    public BigDecimal getFirmValue() {
        return firmValue;
    }

    public Integer getFoundationYear() {
        return foundationYear;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIpo() {
        return ipo;
    }

    public String getMarketSegment() {
        return marketSegment;
    }

    public String getName() {
        return name;
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
