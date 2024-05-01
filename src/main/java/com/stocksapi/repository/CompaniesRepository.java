package com.stocksapi.repository;

import com.stocksapi.model.Companies;
import com.stocksapi.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompaniesRepository extends JpaRepository<Companies, Integer> {

    @Query("SELECT c FROM Companies c WHERE c.id = :id")
    Optional<Companies> findById(@Param("id") Integer id);

}
