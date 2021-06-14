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

	@RequestMapping(value = "/company/addOrRemoveSector", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addOrRemoveSector(@RequestBody JsonNode jsonNode) {

		if (jsonNode.get("companyId") == null) {
			return "companyId must not be null";
		}

		Long companyId = jsonNode.get("companyId").asLong();
		Company company = service.findById(companyId);
		if (company == null) {
			return "Company Does Not  Exist";
		}

		if (jsonNode.get("sectorId") == null) {
			company.setSector(null);
			service.addCompany(company);
			return "Company removed from to sector";

		} else {
			Long sectorId = jsonNode.get("sectorId").asLong();
			Sector sector = sectorService.findById(sectorId);
			if (sector != null) {
				company.setSector(sector);
				service.addCompany(company);
			} else {
				return "Sector Does Not Exist";
			}
			return "Company Added to sector";
		}

	}

	/*
	 * ADD TO STOCK EXCHANGE BY GIVING STOCK CODE IS IMPLEMENTED IN STOCKCODE
	 * SERVICE
	 */

	@RequestMapping(value = "/company/remove", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String add(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("companyId") == null) {
			return "companyId must not be null";
		}
		Long companyId = jsonNode.get("companyId").asLong();
		Company company = service.findById(companyId);
		if (company != null) {
			service.removeById(companyId);
			return "COMPANY REMOVED";
		} else {
			return "COMPANY DOES NOT EXIST";
		}
	}

	@RequestMapping(value = "/company/editBasic", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String editBasicCompany(@RequestBody Company companyBasicData) {
		boolean isUpdated = service.updateCompanyBasicInfo(companyBasicData);
		if (isUpdated) {
			return "Company Data Updated";
		} else {
			return "Update Failed";
		}
	}

}
