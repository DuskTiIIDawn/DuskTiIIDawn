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
}
