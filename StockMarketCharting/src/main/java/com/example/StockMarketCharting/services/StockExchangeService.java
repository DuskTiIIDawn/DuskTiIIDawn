package com.example.StockMarketCharting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.repositories.StockExchangeRepository;

@Service
public class StockExchangeService {

	@Autowired
	StockExchangeRepository stockExchangeRepository;

}
