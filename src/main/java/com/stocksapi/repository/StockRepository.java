package com.stocksapi.repository;

import com.stocksapi.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stocks, Integer> {

    Optional<Stocks> findByTicker(String ticker);

    @Query("SELECT s FROM Stocks s WHERE s.companies.id = :companyId")
    Optional<Stocks> findByCompanyId(@Param("companyId") Integer companyId);

    @Query("SELECT s FROM Stocks s LEFT JOIN s.companies c " +
            "WHERE (:companyName IS NULL OR c.name = :companyName) " +
            "AND c.firmValue <= 10000000000 " +
            "AND (:sector IS NULL OR c.sector = :sector) " +
            "AND (:ticker IS NULL OR s.ticker = :ticker)")
    List<Stocks> searchSmall(@Param("companyName") String companyName,
                             @Param("sector") String sector,
                             @Param("ticker") String ticker);


    @Query("SELECT s " +
            "FROM Stocks s " +
            "LEFT JOIN Companies c ON c.id = s.companies.id " +
            "WHERE (:companyName IS NULL OR c.name = :companyName) " +
            "AND c.firmValue >= 10000000000 " +
            "AND (:sector IS NULL OR c.sector = :sector) " +
            "AND (:ticker IS NULL OR s.ticker = :ticker)")
    List<Stocks> searchLarge(@Param("companyName")  String companyName, @Param("sector") String sector, @Param("ticker") String ticker);


}
