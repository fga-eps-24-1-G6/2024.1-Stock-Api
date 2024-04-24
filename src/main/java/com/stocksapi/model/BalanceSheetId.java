package com.stocksapi.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BalanceSheetId implements Serializable {

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "quarter")
    private Integer quarter;

    public BalanceSheetId() {
    }

    public BalanceSheetId(Long companyId, Integer year, Integer quarter) {
        this.companyId = companyId;
        this.year = year;
        this.quarter = quarter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceSheetId that = (BalanceSheetId) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(year, that.year) &&
                Objects.equals(quarter, that.quarter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, year, quarter);
    }
}
