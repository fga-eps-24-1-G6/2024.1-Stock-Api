package com.stocksapi.repository;

import com.stocksapi.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stocks, Integer> {

    Optional<Stocks> findByTicker(String ticker);

    @Query("SELECT s FROM Stocks s WHERE s.companies.id = :companyId")
    Optional<Stocks> findByCompanyId(@Param("companyId") Integer companyId);
}
