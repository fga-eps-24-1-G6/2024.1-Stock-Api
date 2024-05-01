package com.stocksapi.repository;

import com.stocksapi.model.BalanceSheet;
import com.stocksapi.model.BalanceSheetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BalanceSheetsRepository extends JpaRepository<BalanceSheet, Integer> {

    @Query("SELECT bs FROM BalanceSheet bs WHERE bs.companies.id = :companyId ORDER BY bs.year DESC, bs.quarter DESC ")
    Optional<BalanceSheet[]> findLatestByCompanyId(@Param("companyId") Integer companyId);

    default BalanceSheet[] findLatestBalanceSheetByCompanyId(Integer companyId) {
        return findLatestByCompanyId(companyId)
                .orElse(null);
    }

    List<BalanceSheet> findAllByCompaniesId(Integer companyId);

}
