package com.stocksapi.repository;

import com.stocksapi.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stocks, Integer> {

    Optional<Stocks> findByTicker(String ticker);
}
