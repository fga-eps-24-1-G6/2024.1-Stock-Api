package com.stocksapi.repository;

import com.stocksapi.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Prices, Integer> {
    @Query("SELECT p FROM Prices p WHERE p.stocks.id = :id AND p.priceDate = (SELECT MAX(p2.priceDate) FROM Prices p2 WHERE p2.stocks.id = :id)")
    Optional<Prices> findLatestPriceByStocksId(Integer id);

    List<Prices> findAllByStocksIdOrderByPriceDate(Integer id);
}
