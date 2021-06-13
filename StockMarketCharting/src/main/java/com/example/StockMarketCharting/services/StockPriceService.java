package com.example.StockMarketCharting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.StockPrice;
import com.example.StockMarketCharting.repositories.StockPriceRepository;

@Service
public class StockPriceService {

	@Autowired
	StockPriceRepository stockPriceRepository;

	public void addStockPrice(StockPrice stockPrice) {
		stockPriceRepository.save(stockPrice);
	}

}
