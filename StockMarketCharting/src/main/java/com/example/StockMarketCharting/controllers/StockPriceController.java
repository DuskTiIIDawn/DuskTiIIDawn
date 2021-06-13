package com.example.StockMarketCharting.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.example.StockMarketCharting.entities.StockPrice;
import com.example.StockMarketCharting.services.StockCodeService;
import com.example.StockMarketCharting.services.StockPriceService;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class StockPriceController {

	@Autowired
	StockPriceService service;

	@Autowired
	StockCodeService stockCodeService;

	@RequestMapping(value = "/importdata/upload", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public Map<String, Boolean> addStockExchange(@RequestBody Map<String, JsonNode> requestMap) {
		Map<String, Boolean> response = new HashMap<>();
		for (String dataNo : requestMap.keySet()) {
			Long stockCodeNo = requestMap.get(dataNo).get("stockCodeNo").asLong();
			double currentPrice = requestMap.get(dataNo).get("currentPrice").asDouble();
			String dateTimeStr = requestMap.get(dataNo).get("dateAndTime").asText();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

			boolean isPresent = stockCodeService.checkByStockCode(stockCodeNo);
			if (isPresent) {
				StockCode stockCode = stockCodeService.findByStockCode(stockCodeNo);
				StockPrice stockPrice = new StockPrice(currentPrice, dateTime);
				stockPrice.setStockCode(stockCode);
				service.addStockPrice(stockPrice);
				response.put(dataNo, true);
			} else {
				response.put(dataNo, false);
			}

		}

		return response;
	}

}
