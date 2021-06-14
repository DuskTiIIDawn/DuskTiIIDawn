package com.example.StockMarketCharting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.repositories.StockCodeRepository;

@Service
public class StockCodeService {

	@Autowired
	StockCodeRepository stockCodeRepository;

	public void addStockCode(StockCode stockCode) {
		stockCodeRepository.save(stockCode);
	}

	public StockCode findByStockCode(long l) {
		if (stockCodeRepository.findByStockCode(l).size() > 0) {
			return stockCodeRepository.findByStockCode(l).get(0);
		} else
			return null;
	}

	public boolean removeStockCode(Long stockCodeId) {
		if (stockCodeRepository.findById(stockCodeId).isPresent() == true) {
			stockCodeRepository.deleteById(stockCodeId);
			return true;
		} else
			return false;
	}

}
