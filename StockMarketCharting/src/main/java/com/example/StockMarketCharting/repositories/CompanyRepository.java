package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
