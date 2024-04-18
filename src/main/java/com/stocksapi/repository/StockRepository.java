package com.stocksapi.repository;

import com.stocksapi.model.Stocks;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stocks, Integer> {

    Optional<Stocks> findByTicker(String ticker);
}
