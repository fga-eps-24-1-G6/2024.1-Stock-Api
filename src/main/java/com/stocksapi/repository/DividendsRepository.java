package com.stocksapi.repository;

import com.stocksapi.model.Dividends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DividendsRepository extends JpaRepository<Dividends, Integer> {
    @Query("SELECT d FROM Dividends d WHERE d.stocks.id = :stockId")
    List<Dividends> findByStocksId(Integer stockId);
}
