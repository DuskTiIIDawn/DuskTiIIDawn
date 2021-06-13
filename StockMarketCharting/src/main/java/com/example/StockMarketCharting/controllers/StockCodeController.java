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

import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.services.StockCodeService;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class StockCodeController {

	@Autowired
	StockCodeService service;

	@RequestMapping(value = "/stockCode/getInfo", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public Map<String, String> manageCompanies(@RequestBody JsonNode jsonNode) {
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

}
