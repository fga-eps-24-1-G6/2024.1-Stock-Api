package com.stocksapi.repository;

import com.stocksapi.model.Companies;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompaniesRepository extends CrudRepository<Companies, Integer> {

    List<Companies> findAll();
}
