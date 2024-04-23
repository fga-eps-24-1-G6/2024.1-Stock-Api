package com.stocksapi.repository;

import com.stocksapi.model.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompaniesRepository extends JpaRepository<Companies, Integer> {

}
