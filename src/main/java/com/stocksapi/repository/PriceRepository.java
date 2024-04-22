package com.stocksapi.repository;

import com.stocksapi.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Prices, Integer> {
    Optional<Prices> findByStocksId(Integer id);
}
