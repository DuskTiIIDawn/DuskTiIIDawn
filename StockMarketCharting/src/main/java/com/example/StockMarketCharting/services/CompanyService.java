package com.example.StockMarketCharting.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.repositories.CompanyRepository;

@Service
@Transactional
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	public void addCompany(Company company) {
		companyRepository.save(company);
	}
	/*
	 * public boolean updateCompany(Company company) { Optional<Company> c =
	 * companyRepository.findById((Long) company.getId()); if (c.isPresent()) {
	 * Company dataCompany = c.get(); dataCompany = company;
	 * companyRepository.save(dataCompany); return true; } else { return false; }
	 * 
	 * }
	 * 
	 */

	public List<Company> findallCompanies() {
		List<Company> companyList = companyRepository.findAll();
		return companyList;
	}

	public Company findById(long l) {
		Optional<Company> ose = companyRepository.findById(l);
		if (ose.isPresent()) {
			return ose.get();
		} else {
			return null;
		}

	}

	public List<Company> findByCompanyNameContaining(String str) {
		List<Company> companies = companyRepository.findByCompanyNameContaining(str);
		return companies;

	}

}
