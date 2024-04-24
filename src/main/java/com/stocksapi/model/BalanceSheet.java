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
import java.math.BigInteger;
import java.math.BigInteger;

@Entity
@Table(name = "balance_sheets", schema = "public")
public class BalanceSheet {
    @EmbeddedId
    private BalanceSheetId id;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Companies companies;

    @Column(name = "net_revenue")
    private BigInteger netRevenue;

    @Column(name = "costs")
    private BigInteger costs;

    @Column(name = "gross_profit")
    private BigInteger grossProfit;

    @Column(name = "net_profit")
    private BigInteger netProfit;

    @Column(name = "ebitda")
    private BigInteger ebitda;

    @Column(name = "ebit")
    private BigInteger ebit;

    @Column(name = "taxes")
    private BigInteger taxes;

    @Column(name = "gross_debt")
    private BigInteger grossDebt;

    @Column(name = "net_debt")
    private BigInteger netDebt;

    @Column(name = "equity")
    private BigInteger equity;

    @Column(name = "cash")
    private BigInteger cash;

    @Column(name = "assets")
    private BigInteger assets;

    @Column(name = "liabilities")
    private BigInteger liabilities;

    public BalanceSheet() {
    }

    public BalanceSheet(BigInteger assets, BigInteger cash, Companies companies, BigInteger costs, BigInteger ebit, BigInteger ebitda, BigInteger equity, BigInteger grossDebt, BigInteger grossProfit, BalanceSheetId id, BigInteger liabilities, BigInteger netDebt, BigInteger netProfit, BigInteger netRevenue, BigInteger taxes) {
        this.assets = assets;
        this.cash = cash;
        this.companies = companies;
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
        this.taxes = taxes;
    }

    public BigInteger getAssets() {
        return assets;
    }

    public BigInteger getCash() {
        return cash;
    }

    public Companies getCompanies() {
        return companies;
    }

    public BigInteger getCosts() {
        return costs;
    }

    public BigInteger getEbit() {
        return ebit;
    }

    public BigInteger getEbitda() {
        return ebitda;
    }

    public BigInteger getEquity() {
        return equity;
    }

    public BigInteger getGrossDebt() {
        return grossDebt;
    }

    public BigInteger getGrossProfit() {
        return grossProfit;
    }

    public BalanceSheetId getId() {
        return id;
    }

    public BigInteger getLiabilities() {
        return liabilities;
    }

    public BigInteger getNetDebt() {
        return netDebt;
    }

    public BigInteger getNetProfit() {
        return netProfit;
    }

    public BigInteger getNetRevenue() {
        return netRevenue;
    }

    public BigInteger getTaxes() {
        return taxes;
    }
}
