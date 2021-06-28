package com.example.StockMarketCharting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByCompanyNameContainingIgnoreCase(String companyName);

	List<Company> findBySector_IdAndCompanyNameContaining(Long sectorId, String companyName);

}
