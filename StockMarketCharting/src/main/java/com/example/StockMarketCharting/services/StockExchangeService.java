package com.example.StockMarketCharting.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.StockExchange;
import com.example.StockMarketCharting.repositories.StockExchangeRepository;

@Service
public class StockExchangeService {

	@Autowired
	StockExchangeRepository stockExchangeRepository;

	public void addStockExchange(StockExchange stockExchange) {
		stockExchangeRepository.save(stockExchange);
	}

	public List<StockExchange> findallStockExchange() {
		List<StockExchange> stockExchangeList = stockExchangeRepository.findAll();
		return stockExchangeList;
	}

	public StockExchange findById(long l) {
		Optional<StockExchange> ose = stockExchangeRepository.findById(l);
		if (ose.isPresent()) {
			return ose.get();
		} else {
			return null;
		}
	}

	public boolean updateStockExchangeBasicInfo(StockExchange stockExchangeData) {
		Long id = (Long) stockExchangeData.getId();
		if (id == null)
			return false;
		Optional<StockExchange> s = stockExchangeRepository.findById(id);
		if (s.isPresent()) {
			StockExchange dataRepoStockExchange = s.get();
			dataRepoStockExchange.setStockExchangeName(stockExchangeData.getStockExchangeName());
			dataRepoStockExchange.setBrief(stockExchangeData.getBrief());
			dataRepoStockExchange.setContactAddress(stockExchangeData.getContactAddress());
			dataRepoStockExchange.setRemarks(stockExchangeData.getRemarks());
			stockExchangeRepository.save(dataRepoStockExchange);
			return true;
		} else {
			return false;
		}

	}
}
