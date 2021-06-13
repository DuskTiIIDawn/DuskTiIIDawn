package com.example.StockMarketCharting.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.Sector;
import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.entities.StockExchange;
import com.example.StockMarketCharting.services.CompanyService;
import com.example.StockMarketCharting.services.SectorService;
import com.example.StockMarketCharting.services.StockCodeService;
import com.example.StockMarketCharting.services.StockExchangeService;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class CompanyController {

	@Autowired
	CompanyService service;

	@Autowired
	StockCodeService stockCodeService;

	@Autowired
	StockExchangeService stockExchangeService;

	@Autowired
	SectorService sectorService;

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin("*")
	public List<Company> manageCompany(@RequestBody Company company) {
		List<Company> companyList = service.findallCompanies();
		return companyList;
	}

	@RequestMapping(value = "/company/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addCompany(@RequestBody Company company) {
		service.addCompany(company);
		return "Company Added";
	}

	/*
	 * @RequestMapping(value = "/manageCompany/edit", method = RequestMethod.POST)
	 * 
	 * @ResponseBody
	 * 
	 * @CrossOrigin("*") public String updateCompany(@RequestBody Company company) {
	 * boolean isUpdated = service.updateCompany(company); if (isUpdated) { return
	 * "Company Updated"; } else { return "Update Failed"; } }
	 */

	@RequestMapping(value = "/company/addToStockExchange", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addStockExchange(@RequestBody JsonNode jsonNode) {
		Long companyId = jsonNode.get("companyId").asLong();
		Long stockExchangeId = jsonNode.get("stockExchangeId").asLong();
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockExchange stockExchange = stockExchangeService.findById(stockExchangeId);
		Company company = service.findById(companyId);

		StockCode stockCode = new StockCode(stockCodeNo);
		stockCode.setCompany(company);
		stockCode.setStockExchange(stockExchange);
		stockCode.setStockCode(stockCodeNo);
		stockCodeService.addStockCode(stockCode);
		return "Stock Code Added";
	}

	@RequestMapping(value = "/company/getDetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public Map<String, Object> getDetails(@RequestBody JsonNode jsonNode) {
		Long companyId = jsonNode.get("companyId").asLong();
		Map<String, Object> response = service.getCompanyDetails(companyId);
		return response;
	}

	@RequestMapping(value = "/company/addToSector", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public boolean addToSector(@RequestBody JsonNode jsonNode) {
		Long companyId = jsonNode.get("companyId").asLong();
		Long sectorId = jsonNode.get("sectorId").asLong();
		Company company = service.findById(companyId);
		Sector sector = sectorService.findById(sectorId);
		if (company != null && sector != null) {
			company.setSector(sector);
			sectorService.addSector(sector);
			return true;
		}
		return false;
	}

}
