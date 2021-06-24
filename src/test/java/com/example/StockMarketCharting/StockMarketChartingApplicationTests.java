package com.example.StockMarketCharting;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.services.CompanyService;

class StockMarketChartingApplicationTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CompanyService service;

	void contextLoads() {
		Company company = service.findById(4L);

		List<StockCode> stockCodes = company.getStockCodes();
		logger.info("Stock Codes Retrieved {}", stockCodes);

		/*
		 * List<StockExchange> stockExchanges = new ArrayList<>(); for (StockCode
		 * stockCode : stockCodes) { stockExchanges.add(stockCode.getStockExchange()); }
		 * logger.info("Stock Exchange Retrieved {}", stockExchanges);
		 */
	}
}
