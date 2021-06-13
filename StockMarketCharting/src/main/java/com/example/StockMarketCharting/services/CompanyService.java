package com.example.StockMarketCharting.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.Sector;
import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.entities.StockExchange;
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

	@Transactional
	public Map<String, Object> getCompanyDetails(Long companyId) {
		Map<String, Object> result = new HashMap<>();
		Company company = this.findById(companyId);
		if (company != null) {
			result.put("company", company);
			List<StockCode> stockCodes = company.getStockCodes();
			if (stockCodes.size() > 0) {
				List<StockExchange> stockExchanges = new ArrayList<>();
				for (StockCode stockCode : stockCodes) {
					stockExchanges.add(stockCode.getStockExchange());
				}
				result.put("stockCodes", stockCodes);
				result.put("stockExchanges", stockExchanges);
			}
			Sector sector = company.getSector();
			if (sector != null) {
				result.put("sector", sector);
			}

		}
		return result;

	}

}
