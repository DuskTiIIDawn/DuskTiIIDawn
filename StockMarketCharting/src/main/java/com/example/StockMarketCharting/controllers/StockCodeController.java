package com.example.StockMarketCharting.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.entities.StockExchange;
import com.example.StockMarketCharting.services.CompanyService;
import com.example.StockMarketCharting.services.StockCodeService;
import com.example.StockMarketCharting.services.StockExchangeService;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class StockCodeController {

	@Autowired
	StockCodeService service;

	@Autowired
	StockExchangeService stockExchangeService;

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/stockCode/getInfo", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public Map<String, String> getInfo(@RequestBody JsonNode jsonNode) {
		Map<String, String> response = new HashMap<>();
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = service.findByStockCode(stockCodeNo);
		if (stockCode != null) {
			String companyName = stockCode.getCompany().getCompanyName();
			String StockExchangeName = stockCode.getStockExchange().getStockExchangeName();
			response.put("isPresent", "YES");
			response.put("companyName", companyName);
			response.put("stockExchangeName", StockExchangeName);
		} else {
			response.put("isPresent", "NO");
		}
		return response;
	}

	@RequestMapping(value = "/stockCode/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addStockCode(@RequestBody JsonNode jsonNode) {

		if (jsonNode.get("companyId") == null || jsonNode.get("stockExchangeId") == null
				|| jsonNode.get("stockCodeNo") == null) {
			return "companyId ,stockExchangeId ,and stockCodeNo must not be null";
		}
		Long companyId = jsonNode.get("companyId").asLong();
		Long stockExchangeId = jsonNode.get("stockExchangeId").asLong();
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = service.findByStockCode(stockCodeNo);
		if (stockCode != null) {
			return "Error: Please Use different Stock Code No. It already exist";
		}
		StockExchange stockExchange = stockExchangeService.findById(stockExchangeId);
		Company company = companyService.findById(companyId);
		if (company != null && stockExchange != null) {
			StockCode newstockCode = new StockCode(stockCodeNo);
			newstockCode.setCompany(company);
			newstockCode.setStockExchange(stockExchange);
			newstockCode.setStockCode(stockCodeNo);
			service.addStockCode(newstockCode);
			return "Stock Code ADDED";
		} else {
			return company == null ? "Company Does Not Exist" : "Stock Exchange Does Not Exist";
		}
	}

	@RequestMapping(value = "/stockCode/remove", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public Boolean removeStockCode(@RequestBody JsonNode jsonNode) {
		Long stockCodeId = jsonNode.get("stockCodeId").asLong();
		return service.removeStockCode(stockCodeId);
	}

}
