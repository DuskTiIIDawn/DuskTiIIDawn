package com.example.StockMarketCharting.controllers;

import static com.monitorjbl.json.Match.match;

import java.util.ArrayList;
import java.util.List;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewModule;

@Controller
public class StockCodeController {
	private ObjectMapper mapper = new ObjectMapper().registerModules(new JsonViewModule(), new JavaTimeModule());

	@Autowired
	StockCodeService service;

	@Autowired
	StockExchangeService stockExchangeService;

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/stockCode/getAll", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String getAllByCompanyIdOrStockExchangeId(@RequestBody JsonNode jsonNode) {
		List<StockCode> stockCodes = new ArrayList<>();
		String json = null;
		if (jsonNode.get("companyId") != null) {
			Long companyId = jsonNode.get("companyId").asLong();
			stockCodes = service.findByCompanyId(companyId);
			try {
				json = mapper.writeValueAsString(
						JsonView.with(stockCodes).onClass(StockCode.class, match().include("stockExchange"))
								.onClass(StockExchange.class, match().exclude("*").include("id", "stockExchangeName")));
			} catch (JsonProcessingException e) {
				return e.getMessage();
			}
		} else if (jsonNode.get("stockExchangeId") != null) {
			Long stockExchangeId = jsonNode.get("stockExchangeId").asLong();
			stockCodes = service.findByStockExchangeId(stockExchangeId);
			try {
				json = mapper.writeValueAsString(
						JsonView.with(stockCodes).onClass(StockCode.class, match().include("company"))
								.onClass(Company.class, match().exclude("*").include("id", "companyName")));
			} catch (JsonProcessingException e) {
				return e.getMessage();
			}
		}

		return json;
	}

	@RequestMapping(value = "/stockCode/getInfo", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String getInfo(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("stockCodeNo") == null) {
			return "stockCodeNo must not be null";
		}
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = service.findByStockCode(stockCodeNo);
		if (stockCode != null) {
			try {
				String json = mapper.writeValueAsString(
						JsonView.with(stockCode).onClass(StockCode.class, match().include("company", "stockExchange"))
								.onClass(Company.class, match().exclude("*").include("id", "companyName"))
								.onClass(StockExchange.class, match().exclude("*").include("id", "stockExchangeName")));
				return json;
			} catch (JsonProcessingException e) {

				return e.getMessage();
			}
		} else {
			return "No records Available for this Stock Code No";
		}

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
