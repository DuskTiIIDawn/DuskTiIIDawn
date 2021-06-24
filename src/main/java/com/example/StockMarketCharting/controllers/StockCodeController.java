package com.example.StockMarketCharting.controllers;

import static com.monitorjbl.json.Match.match;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
	public String getAllByCompanyIdOrStockExchangeId(@RequestBody JsonNode jsonNode) {
		List<StockCode> stockCodes = new ArrayList<>();

		if (jsonNode.get("companyId") != null && jsonNode.get("stockExchangeId") == null) {
			Long companyId = jsonNode.get("companyId").asLong();
			stockCodes = service.findByCompanyId(companyId);

		} else if (jsonNode.get("stockExchangeId") != null && jsonNode.get("companyId") == null) {
			Long stockExchangeId = jsonNode.get("stockExchangeId").asLong();
			stockCodes = service.findByStockExchangeId(stockExchangeId);

		} else if (jsonNode.get("stockExchangeId") != null && jsonNode.get("companyId") != null) {

			stockCodes = service.findByStockExchangeIdAndCompanyId(jsonNode.get("stockExchangeId").asLong(),
					jsonNode.get("companyId").asLong());

		} else {
			stockCodes = service.findAll();
		}
		try {
			return mapper.writeValueAsString(
					JsonView.with(stockCodes).onClass(StockCode.class, match().include("company", "stockExchange"))
							.onClass(Company.class, match().exclude("*").include("id", "companyName"))
							.onClass(StockExchange.class, match().exclude("*").include("id", "stockExchangeName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/stockCode/getInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getInfo(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("stockCodeNo") == null) {
			return "stockCodeNo must not be null";
		}
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = service.findByStockCode(stockCodeNo);
		if (stockCode != null) {
			try {
				return mapper.writeValueAsString(
						JsonView.with(stockCode).onClass(StockCode.class, match().include("company", "stockExchange"))
								.onClass(Company.class, match().exclude("*").include("id", "companyName"))
								.onClass(StockExchange.class, match().exclude("*").include("id", "stockExchangeName")));

			} catch (JsonProcessingException e) {

				return e.getMessage();
			}
		} else {
			return "No records Available for this Stock Code No";
		}

	}

	@RequestMapping(value = "/stockCode/addUpdate", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addStockCode(@RequestBody JsonNode jsonNode) {

		if (jsonNode.get("stockCodeNo") == null) {
			return "StockCodeNo Required";
		}
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = service.findByStockCode(stockCodeNo);
		if (stockCode != null) {
			return "Error: Please Use different Stock Code No. It already exist";
		}
		if (jsonNode.get("stockCodeId") != null) {
			Long stockCodeId = jsonNode.get("stockCodeId").asLong();
			StockCode stockCodeRepo = service.findByStockCodeId(stockCodeId);
			stockCodeRepo.setStockCode(stockCodeNo);
			service.addStockCode(stockCodeRepo);
			return "Stock Code Updated";
		}

		if (jsonNode.get("companyId") == null || jsonNode.get("stockExchangeId") == null) {
			return "companyId ,stockExchangeId  must not be null";
		}
		Long companyId = jsonNode.get("companyId").asLong();
		Long stockExchangeId = jsonNode.get("stockExchangeId").asLong();

		StockExchange stockExchange = stockExchangeService.findById(stockExchangeId);
		Company company = companyService.findById(companyId);
		if (company != null && stockExchange != null && jsonNode.get("stockCodeId") == null) {
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String removeStockCode(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("stockCodeId") == null) {
			return "Stock  Code Id Must Not Be Null";
		}
		Long stockCodeId = jsonNode.get("stockCodeId").asLong();
		if (service.removeStockCode(stockCodeId)) {
			return "Stock Code Removed";
		} else {
			return "Request To remove failed";
		}
	}

}
