package com.stocksapi.repository;

import com.stocksapi.model.Dividends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DividendsRepository extends JpaRepository<Dividends, Integer> {
    Optional<Dividends> findByStocksId(Integer id);
}
