package com.example.StockMarketCharting.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.IPODetail;
import com.example.StockMarketCharting.entities.StockExchange;
import com.example.StockMarketCharting.repositories.IPODetailRepository;

@Service
public class IPODetailService {

	@Autowired
	IPODetailRepository ipoDetailRepository;

	@Autowired
	CompanyService companyService;

	@Autowired
	StockExchangeService stockExchangeService;

	public IPODetail findById(Long id) {
		Optional<IPODetail> oipo = ipoDetailRepository.findById(id);
		if (oipo.isPresent()) {
			return oipo.get();
		} else {
			return null;
		}
	}

	public List<IPODetail> findallIPO() {
		List<IPODetail> ipoList = ipoDetailRepository.findAll();
		return ipoList;
	}

	@Transactional
	public String addIPO(Long companyId, List<Long> stockExchangeIds, IPODetail ipoDetail) {
		List<StockExchange> stockExchanges = new ArrayList<StockExchange>();
		for (Long stockExchangeId : stockExchangeIds) {
			StockExchange stockExchange = stockExchangeService.findById(stockExchangeId);
			if (stockExchange != null) {
				stockExchanges.add(stockExchange);
			}
		}
		ipoDetail.setStockExchanges(stockExchanges);
		Company company = companyService.findById(companyId);
		if (company == null) {
			return "Company Does Not Exist";
		} else if (company.getIpo() != null) {
			return "This Company Already has an IPO";
		} else {
			ipoDetail.setCompany(company);
			ipoDetailRepository.save(ipoDetail);
			return "IPO ADDED";
		}

	}

	@Transactional
	public String UpdateIPO(Long companyId, List<Long> stockExchangeIds, IPODetail ipoDetailData, Long ipoDetailId) {
		IPODetail ipoDetail = this.findById(ipoDetailId);
		if (ipoDetail != null) {
			ipoDetail.setTotalNumberOfShares(ipoDetailData.getTotalNumberOfShares());
			ipoDetail.setPricePerShare(ipoDetailData.getPricePerShare());
			ipoDetail.setOpenDateTime(ipoDetailData.getOpenDateTime());
			List<StockExchange> stockExchanges = new ArrayList<StockExchange>();
			for (Long stockExchangeId : stockExchangeIds) {
				StockExchange stockExchange = stockExchangeService.findById(stockExchangeId);
				if (stockExchange != null) {
					stockExchanges.add(stockExchange);
				}
			}
			ipoDetail.setStockExchanges(stockExchanges);
			Company company = companyService.findById(companyId);
			if (company == null) {
				return "Company Does Not Exist";
			} else if (company.getIpo() != null && company.getIpo().getId() != ipoDetailId) {
				return "This Company Already has an IPO";
			} else {
				ipoDetail.setCompany(company);
				ipoDetailRepository.save(ipoDetail);
				return "IPO UPDATED";
			}
		} else {
			return "IPO with this ipodetailId does not exist";
		}

	}

	public boolean removeIPO(Long ipoDetailId) {
		if (this.findById(ipoDetailId) != null) {
			ipoDetailRepository.deleteById(ipoDetailId);
			return true;
		} else
			return false;
	}

}
