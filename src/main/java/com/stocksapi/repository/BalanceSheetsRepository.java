package com.stocksapi.repository;

import com.stocksapi.model.BalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BalanceSheetsRepository extends JpaRepository<BalanceSheet, Integer> {

    @Query("SELECT bs FROM BalanceSheet bs WHERE bs.companies.id = :companyId " +
            "AND (bs.year, bs.quarter) = (SELECT MAX(bs2.year), MAX(bs2.quarter) " +
            "FROM BalanceSheet bs2 WHERE bs2.companies.id = :companyId)")
    Optional<BalanceSheet> findLatestByCompanyId(@Param("companyId") Integer companyId);

    default BalanceSheet findLatestBalanceSheetByCompanyId(Integer companyId) {
        return findLatestByCompanyId(companyId)
                .orElse(null);
    }
}
