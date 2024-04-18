package com.stocksapi.service;

import com.stocksapi.model.Companies;
import com.stocksapi.repository.CompaniesRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CompaniesService {

    private final CompaniesRepository companiesRepository;

    public CompaniesService(CompaniesRepository companiesRepository) {
        this.companiesRepository = companiesRepository;
    }

    public Stream<Companies> findAll(){
        return companiesRepository.findAll().stream();
    }
}
