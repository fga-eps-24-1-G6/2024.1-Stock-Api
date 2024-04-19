package com.stocksapi.repository;

import com.stocksapi.model.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompaniesRepository extends JpaRepository<Companies, Integer> {

    List<Companies> findAll();
}
