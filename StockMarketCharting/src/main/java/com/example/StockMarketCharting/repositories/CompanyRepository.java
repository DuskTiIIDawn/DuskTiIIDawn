package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
