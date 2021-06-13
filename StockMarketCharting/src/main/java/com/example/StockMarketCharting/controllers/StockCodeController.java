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
	public Boolean addStockCode(@RequestBody JsonNode jsonNode) {
		boolean response = false;
		Long companyId = jsonNode.get("companyId").asLong();
		Long stockExchangeId = jsonNode.get("stockExchangeId").asLong();
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockExchange stockExchange = stockExchangeService.findById(stockExchangeId);
		Company company = companyService.findById(companyId);
		if (company != null && stockExchange != null) {
			StockCode stockCode = new StockCode(stockCodeNo);
			stockCode.setCompany(company);
			stockCode.setStockExchange(stockExchange);
			stockCode.setStockCode(stockCodeNo);
			service.addStockCode(stockCode);
			response = true;
		}
		return response;
	}

	@RequestMapping(value = "/stockCode/remove", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public Boolean removeStockCode(@RequestBody JsonNode jsonNode) {
		Long stockCodeId = jsonNode.get("stockCodeId").asLong();
		return service.removeStockCode(stockCodeId);
	}

}
