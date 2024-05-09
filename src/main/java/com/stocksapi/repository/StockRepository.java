package com.stocksapi.repository;

import com.stocksapi.model.Stocks;
import com.stocksapi.dto.SearchedStocksResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stocks, Integer> {

    Optional<Stocks> findByTicker(String ticker);

    @Query("SELECT s FROM Stocks s WHERE s.companies.id = :companyId")
    Optional<Stocks> findByCompanyId(@Param("companyId") Integer companyId);

    @Query("SELECT NEW com.stocksapi.dto.SearchedStocksResponse(s.id, s.ticker, c.name, c.sector, c.id) " +
            "FROM Stocks s " +
            "INNER JOIN s.companies c " +
            "WHERE c.firmValue <= 10000000000")
    List<SearchedStocksResponse> searchAllSmall();

    @Query("SELECT NEW com.stocksapi.dto.SearchedStocksResponse(s.id, s.ticker, c.name, c.sector, c.id) " +
            "FROM Stocks s " +
            "INNER JOIN s.companies c " +
            "WHERE c.firmValue >= 10000000000 ")
    List<SearchedStocksResponse> searchAllLarge();

    @Query("SELECT NEW com.stocksapi.dto.SearchedStocksResponse(s.id, s.ticker, c.name, c.sector, c.id) " +
            "FROM Stocks s " +
            "INNER JOIN s.companies c ")
    List<SearchedStocksResponse> searchAll();
}
