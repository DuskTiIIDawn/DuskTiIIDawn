package com.example.StockMarketCharting.controllers;

import static com.monitorjbl.json.Match.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.IPODetail;
import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.entities.StockExchange;
import com.example.StockMarketCharting.services.StockExchangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewModule;

@Controller
public class StockExchangeController {
	private ObjectMapper mapper = new ObjectMapper().registerModules(new JsonViewModule(), new JavaTimeModule());

	@Autowired
	StockExchangeService service;

	@RequestMapping(value = "/stockExchange", method = RequestMethod.GET)
	@ResponseBody
	public String getAllStockExchange() {
		List<StockExchange> stockExchangeList = service.findallStockExchange();
		try {
			return mapper.writeValueAsString(JsonView.with(stockExchangeList).onClass(StockExchange.class,
					match().exclude("*").include("id", "stockExchangeName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/stockExchange/info", method = RequestMethod.POST)
	@ResponseBody
	public String manageStockExchangeInfo(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("stockExchangeId") == null) {
			return "stockExchangeId must Not be null";
		}
		Long stockExchangeId = jsonNode.get("stockExchangeId").asLong();
		StockExchange stockExchange = service.findById(stockExchangeId);
		if (stockExchange == null) {
			return "Stock Exchange Does Not Exist";
		}
		try {
			return mapper.writeValueAsString(
					JsonView.with(stockExchange).onClass(StockExchange.class, match().include("stockCodes", "ipos"))
							.onClass(StockCode.class, match().exclude("*").include("id", "stockCode", "company"))
							.onClass(IPODetail.class,
									match().exclude("*").include("id", "pricePerShare", "totalNumberOfShares",
											"openDateTime", "company"))
							.onClass(Company.class, match().exclude("*").include("id", "companyName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/stockExchange/getBasicInfo", method = RequestMethod.POST)
	@ResponseBody
	public String manageStockExchangeGetBasicInfo(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("stockExchangeId") == null) {
			return "stockExchangeId must Not be null";
		}
		Long stockExchangeId = jsonNode.get("stockExchangeId").asLong();
		StockExchange stockExchange = service.findById(stockExchangeId);
		if (stockExchange == null) {
			return "Stock Exchange Does Not Exist";
		}
		try {
			return mapper.writeValueAsString(stockExchange);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/stockExchange/add", method = RequestMethod.POST)
	@ResponseBody
	public String addStockExchange(@RequestBody StockExchange stockExchange) {
		service.addStockExchange(stockExchange);
		return "Stock Exchange Added";
	}

	@RequestMapping(value = "/stockExchange/editBasic", method = RequestMethod.POST)
	@ResponseBody
	public String editBasicCompany(@RequestBody StockExchange stockExchangeData) {
		boolean isUpdated = service.updateStockExchangeBasicInfo(stockExchangeData);
		if (isUpdated) {
			return "Stock Exchange Updated";
		} else {
			return "Update Failed";
		}
	}

}
