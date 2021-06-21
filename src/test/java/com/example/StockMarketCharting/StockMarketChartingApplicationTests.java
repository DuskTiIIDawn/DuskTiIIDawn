package com.example.StockMarketCharting;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.services.CompanyService;

@SpringBootTest
class StockMarketChartingApplicationTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CompanyService service;

	@Test

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
