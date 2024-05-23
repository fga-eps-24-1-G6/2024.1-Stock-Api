package com.stocksapi.repository;

import com.stocksapi.model.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BalanceSheetsRepository extends JpaRepository<BalanceSheet, Integer> {
    @Query("SELECT bs.year," +
            "sum(bs.netRevenue) as netRevenue, sum(bs.costs) as costs, sum(bs.grossProfit) as grossProfit, sum(bs.netProfit) as netProfit," +
            "sum(bs.ebitda) as ebitda, sum(bs.ebit) as ebit, sum(bs.taxes) as taxes, sum(bs.grossDebt) as grossDebt, sum(bs.netDebt) as netDebt," +
            "max(bs.equity) as equity, max(bs.cash) as cash, max(bs.assets) as assets, max(bs.liabilities) as liabilities " +
            "FROM BalanceSheet bs " +
            "WHERE bs.companies.id = :companyId " +
            "GROUP BY bs.year " +
            "ORDER BY bs.year DESC")
    List<Object[]> findYearlyByCompanyId(@Param("companyId") Integer companyId);

    @Query("SELECT bs FROM BalanceSheet bs WHERE bs.companies.id = :companyId ORDER BY bs.year DESC, bs.quarter DESC ")
    Optional<BalanceSheet[]> findLatestByCompanyId(@Param("companyId") Integer companyId);

    default BalanceSheet[] findLatestBalanceSheetByCompanyId(Integer companyId) {
        return findLatestByCompanyId(companyId)
                .orElse(null);
    }

    List<BalanceSheet> findAllByCompaniesId(Integer companyId);

}
