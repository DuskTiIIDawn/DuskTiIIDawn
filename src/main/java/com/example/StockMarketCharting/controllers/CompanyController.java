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
import com.example.StockMarketCharting.entities.IPODetail;
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

	@RequestMapping(value = "/company", method = RequestMethod.POST)
	@ResponseBody
	public String getAllCompanyOrSearchByString(@RequestBody JsonNode jsonNode) {

		List<Company> list = new ArrayList<>();
		if (jsonNode.get("search") != null) {
			String search = jsonNode.get("search").asText();
			list = service.findByCompanyNameContaining(search);

		} else {
			list = service.findallCompanies();
		}

		try {
			return mapper.writeValueAsString(JsonView.with(list)
					.onClass(Company.class, match().exclude("*").include("id", "companyName", "sector"))
					.onClass(Sector.class, match().exclude("*").include("id", "sectorName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/company/getAllName", method = RequestMethod.GET)
	@ResponseBody
	public String getAllCompanyNameList() {
		List<Company> list = service.findallCompanies();
		try {
			return mapper.writeValueAsString(
					JsonView.with(list).onClass(Company.class, match().exclude("*").include("id", "companyName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/company/withoutIpoAndWithStockCodes", method = RequestMethod.GET)
	@ResponseBody
	public String getAllCompanyWithoutIpo() {
		List<Company> allList = service.findallCompanies();
		List<Company> list = new ArrayList<>();
		for (Company c : allList) {
			if (c.getIpo() == null && c.getStockCodes().size() > 0) {
				list.add(c);
			}
		}
		try {
			return mapper.writeValueAsString(
					JsonView.with(list).onClass(Company.class, match().exclude("*").include("id", "companyName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/company/getDetails", method = RequestMethod.POST)
	@ResponseBody
	public String getDetails(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("companyId") == null) {
			return "companyId Property Should Not be NULL";
		}
		Long companyId = jsonNode.get("companyId").asLong();
		Company company = service.findById(companyId);
		if (company == null) {
			return "Company Does Not Exist!";
		}

		try {
			return mapper.writeValueAsString(
					JsonView.with(company).onClass(Company.class, match().include("stockCodes", "ipo", "sector"))
							.onClass(Sector.class, match().exclude("*").include("sectorName", "id"))
							.onClass(StockCode.class, match().include("stockExchange"))
							.onClass(IPODetail.class, match().include("stockExchanges"))
							.onClass(StockExchange.class, match().exclude("*").include("id", "stockExchangeName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/company/getBasicInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getBasicInfo(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("companyId") == null) {
			return "companyId Property Should Not be NULL";
		}
		Long companyId = jsonNode.get("companyId").asLong();
		Company company = service.findById(companyId);
		if (company == null) {
			return "Company Does Not Exist";
		}
		try {
			return mapper.writeValueAsString(company);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/company/add", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addCompany(@RequestBody Company company) {
		try {
			service.addCompany(company);
			return "COMPANY ADDED";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/company/editBasic", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String editBasicCompany(@RequestBody Company companyBasicData) {
		boolean isUpdated = service.updateCompanyBasicInfo(companyBasicData);
		if (isUpdated) {
			return "Company Data Updated";
		} else {
			return "Update Failed!";
		}
	}

	@RequestMapping(value = "/company/addOrRemoveSector", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String addOrRemoveSector(@RequestBody JsonNode jsonNode) {

		if (jsonNode.get("companyId") == null) {
			return "companyId must not be null!";
		}

		Long companyId = jsonNode.get("companyId").asLong();
		Company company = service.findById(companyId);
		if (company == null) {
			return "Company Does Not  Exist!";
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
				return "Sector Does Not Exist!";
			}
			return "Company Added to sector";
		}

	}

	/*
	 * ADD TO STOCK EXCHANGE BY GIVING STOCK CODE IS IMPLEMENTED IN STOCKCODE
	 * Controller
	 */

	@RequestMapping(value = "/company/remove", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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

}
