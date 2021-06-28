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

	public void removeById(long l) {
		companyRepository.deleteById(l);
	}

	public List<Company> findByCompanyNameContaining(String str) {
		List<Company> companies = companyRepository.findByCompanyNameContainingIgnoreCase(str);
		return companies;

	}

	public List<Company> findBySector_IdAndCompanyNameContaining(Long sectorId, String str) {
		List<Company> companies = companyRepository.findBySector_IdAndCompanyNameContaining(sectorId, str);
		return companies;

	}

	public boolean updateCompanyBasicInfo(Company companyBasicData) {
		Long id = (Long) companyBasicData.getId();
		if (id == null)
			return false;
		Optional<Company> c = companyRepository.findById(id);
		if (c.isPresent()) {
			Company dataRepoCompany = c.get();
			dataRepoCompany.setCompanyName(companyBasicData.getCompanyName());
			dataRepoCompany.setBoardOfDirectors(companyBasicData.getBoardOfDirectors());
			dataRepoCompany.setCeo(companyBasicData.getCeo());
			dataRepoCompany.setCompanyBrief(companyBasicData.getCompanyBrief());
			dataRepoCompany.setTurnover(companyBasicData.getTurnover());
			companyRepository.save(dataRepoCompany);
			return true;
		} else {
			return false;
		}

	}

}
