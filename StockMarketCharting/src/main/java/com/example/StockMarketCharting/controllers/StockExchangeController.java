package com.example.StockMarketCharting.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.StockExchange;
import com.example.StockMarketCharting.services.StockExchangeService;

@Controller
public class StockExchangeController {

	@Autowired
	StockExchangeService service;

	@RequestMapping(value = "/stockExchange/getall", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin("*")
	public List<StockExchange> manageCompanies() {
		List<StockExchange> stockExchangeList = service.findallStockExchange();
		return stockExchangeList;
	}

	@RequestMapping(value = "/stockExchange/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addStockExchange(@RequestBody StockExchange stockExchange) {
		service.addStockExchange(stockExchange);
		return "Stock Exchange Added";
	}

}
