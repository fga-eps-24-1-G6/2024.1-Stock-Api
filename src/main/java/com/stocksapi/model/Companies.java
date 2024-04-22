package com.stocksapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "companies", schema = "public")
public class Companies {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "ipo")
    private Integer ipo;

    @Column(name = "foundation_year")
    private Integer foundationYear;

    @Column(name = "firm_value")
    private Long firmValue;

    @Column(name = "number_of_papers")
    private Long numberOfPapers;

    @Column(name = "market_segment")
    private String marketSegment;

    @Column(name = "sector")
    private String sector;

    public Companies() {
    }

    public Companies(String cnpj, Long firmValue, Integer foundationYear, Integer id, Integer ipo, String marketSegment, String name, Long numberOfPapers, String sector, String segment) {
        this.cnpj = cnpj;
        this.firmValue = firmValue;
        this.foundationYear = foundationYear;
        this.id = id;
        this.ipo = ipo;
        this.marketSegment = marketSegment;
        this.name = name;
        this.numberOfPapers = numberOfPapers;
        this.sector = sector;
        this.segment = segment;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Long getFirmValue() {
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

    @Column(name = "segment")
    private String segment;
}