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
import com.example.StockMarketCharting.entities.Sector;
import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.entities.StockExchange;
import com.example.StockMarketCharting.services.CompanyService;
import com.example.StockMarketCharting.services.SectorService;
import com.example.StockMarketCharting.services.StockCodeService;
import com.example.StockMarketCharting.services.StockExchangeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewModule;

@Controller
public class CompanyController {
	private ObjectMapper mapper = new ObjectMapper().registerModules(new JsonViewModule(), new JavaTimeModule());

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
	public String getAllCompanyOrSearchByString(@RequestBody JsonNode jsonNode) {

		List<Company> list = new ArrayList<>();
		if (jsonNode.get("search") != null) {
			String search = jsonNode.get("search").asText();
			list = service.findByCompanyNameContaining(search);

		} else {
			list = service.findallCompanies();
		}
		String json;
		try {
			json = mapper.writeValueAsString(
					JsonView.with(list).onClass(Company.class, match().exclude("*").include("id", "companyName")));
		} catch (JsonProcessingException e) {
			json = "error";
		}
		return json;
	}

	@RequestMapping(value = "/company/getDetails", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String getDetails(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("companyId") == null) {
			return "companyId Property Should Not be NULL";
		}
		Long companyId = jsonNode.get("companyId").asLong();
		Company company = service.findById(companyId);
		if (company == null) {
			return "Company Does Not Exist";
		}
		String json;
		try {
			json = mapper.writeValueAsString(
					JsonView.with(company).onClass(Company.class, match().include("stockCodes", "ipo", "sector"))
							.onClass(Sector.class, match().exclude("*").include("sectorName", "id"))
							.onClass(StockCode.class, match().include("stockExchange"))
							.onClass(StockExchange.class, match().exclude("*").include("id", "stockExchangeName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
		return json;

	}

	@RequestMapping(value = "/company/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addCompany(@RequestBody Company company) {
		try {
			service.addCompany(company);
			return "COMPANY ADDED";
		} catch (Exception e) {
			return e.getMessage();
		}

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

	@RequestMapping(value = "/company/addToSector", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addToSector(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("companyId") == null || jsonNode.get("sectorId") == null) {
			return "companyId and sectorId should not be null";
		}
		Long companyId = jsonNode.get("companyId").asLong();
		Long sectorId = jsonNode.get("sectorId").asLong();
		Company company = service.findById(companyId);
		Sector sector = sectorService.findById(sectorId);
		if (company != null && sector != null) {
			company.setSector(sector);
			sectorService.addSector(sector);
			return "ADDED TO SECTOR";
		}
		return "Company or Sector Does Not Exist";
	}

}
