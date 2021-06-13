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

	public boolean checkByStockCode(long l) {
		return stockCodeRepository.findByStockCode(l).size() == 0 ? false : true;
	}

	public StockCode findByStockCode(long l) {
		return stockCodeRepository.findByStockCode(l).get(0);
	}

}
