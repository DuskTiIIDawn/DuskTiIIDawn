package com.example.StockMarketCharting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.repositories.IPODetailRepository;

@Service
public class IPODetailService {

	@Autowired
	IPODetailRepository ipoDetailRepository;

}
