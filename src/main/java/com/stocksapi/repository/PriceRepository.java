package com.stocksapi.repository;

import com.stocksapi.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Prices, Integer> {
    @Query("SELECT p FROM Prices p WHERE p.stockId = :id")
    Optional<Prices> findLatestPriceByStockId(Integer id);

    List<Prices> findAllByStockIdOrderByPriceDate(Integer id);
}
