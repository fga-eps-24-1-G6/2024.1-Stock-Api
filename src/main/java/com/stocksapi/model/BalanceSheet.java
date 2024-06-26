package com.stocksapi.model;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigDecimal;

@Entity
@Table(name = "balance_sheets", schema = "public")
public class BalanceSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "quarter")
    private Integer quarter;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Companies companies;

    @Column(name = "net_revenue")
    private BigDecimal netRevenue;

    @Column(name = "costs")
    private BigDecimal costs;

    @Column(name = "gross_profit")
    private BigDecimal grossProfit;

    @Column(name = "net_profit")
    private BigDecimal netProfit;

    @Column(name = "ebitda")
    private BigDecimal ebitda;

    @Column(name = "ebit")
    private BigDecimal ebit;

    @Column(name = "taxes")
    private BigDecimal taxes;

    @Column(name = "gross_debt")
    private BigDecimal grossDebt;

    @Column(name = "net_debt")
    private BigDecimal netDebt;

    @Column(name = "equity")
    private BigDecimal equity;

    @Column(name = "cash")
    private BigDecimal cash;

    @Column(name = "assets")
    private BigDecimal assets;

    @Column(name = "liabilities")
    private BigDecimal liabilities;

    public BalanceSheet() {
    }

    public BalanceSheet(BigDecimal assets, BigDecimal cash, Companies companies, Long companyId, BigDecimal costs, BigDecimal ebit, BigDecimal ebitda, BigDecimal equity, BigDecimal grossDebt, BigDecimal grossProfit, Integer id, BigDecimal liabilities, BigDecimal netDebt, BigDecimal netProfit, BigDecimal netRevenue, Integer quarter, BigDecimal taxes, Integer year) {
        this.assets = assets;
        this.cash = cash;
        this.companies = companies;
        this.companyId = companyId;
        this.costs = costs;
        this.ebit = ebit;
        this.ebitda = ebitda;
        this.equity = equity;
        this.grossDebt = grossDebt;
        this.grossProfit = grossProfit;
        this.id = id;
        this.liabilities = liabilities;
        this.netDebt = netDebt;
        this.netProfit = netProfit;
        this.netRevenue = netRevenue;
        this.quarter = quarter;
        this.taxes = taxes;
        this.year = year;
    }

    public BigDecimal getAssets() {
        return assets;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public Companies getCompanies() {
        return companies;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public BigDecimal getCosts() {
        return costs;
    }

    public BigDecimal getEbit() {
        return ebit;
    }

    public BigDecimal getEbitda() {
        return ebitda;
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public BigDecimal getGrossDebt() {
        return grossDebt;
    }

    public BigDecimal getGrossProfit() {
        return grossProfit;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getLiabilities() {
        return liabilities;
    }

    public BigDecimal getNetDebt() {
        return netDebt;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public BigDecimal getNetRevenue() {
        return netRevenue;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public Integer getYear() {
        return year;
    }
}