package com.example.StockMarketCharting.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.entities.StockPrice;
import com.example.StockMarketCharting.repositories.StockCodeRepository;

@Service
public class StockCodeService {

	@Autowired
	StockCodeRepository stockCodeRepository;

	public void addStockCode(StockCode stockCode) {
		stockCodeRepository.save(stockCode);
	}

	public StockCode findByStockCode(long l) {
		boolean isPresent = stockCodeRepository.findByStockCode(l).size() == 0 ? false : true;
		if (isPresent) {
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

	@Transactional
	public List<StockPrice> getStockPricesByStockCode(long l) {
		boolean isPresent = stockCodeRepository.findByStockCode(l).size() == 0 ? false : true;
		if (isPresent) {
			return stockCodeRepository.findByStockCode(l).get(0).getStockPrices();
		} else
			return null;
	}

}
