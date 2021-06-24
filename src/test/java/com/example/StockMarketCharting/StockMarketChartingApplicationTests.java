package com.example.StockMarketCharting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.services.CompanyService;

class StockMarketChartingApplicationTests {

	@Autowired
	CompanyService service;

	void contextLoads() {
		Company company = service.findById(4L);

		List<StockCode> stockCodes = company.getStockCodes();

		/*
		 * List<StockExchange> stockExchanges = new ArrayList<>(); for (StockCode
		 * stockCode : stockCodes) { stockExchanges.add(stockCode.getStockExchange()); }
		 * logger.info("Stock Exchange Retrieved {}", stockExchanges);
		 */
	}
}
