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
import com.example.StockMarketCharting.services.CompanyService;
import com.example.StockMarketCharting.services.SectorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewModule;

@Controller
public class SectorController {
	private ObjectMapper mapper = new ObjectMapper().registerModules(new JsonViewModule(), new JavaTimeModule());

	@Autowired
	SectorService sectorService;

	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/sector", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin(origins = "https://stockmarketchartingfrontend.herokuapp.com/")
	public List<Sector> getAll() {
		return sectorService.findAll();
	}

	@RequestMapping(value = "/sector/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "https://stockmarketchartingfrontend.herokuapp.com/")
	public String addSector(@RequestBody Sector sector) {
		sectorService.addSector(sector);
		return "Sector Added";
	}

	@RequestMapping(value = "/sector/editBasic", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "https://stockmarketchartingfrontend.herokuapp.com/")
	public String editBasicOfSector(@RequestBody Sector sectorBasicData) {
		boolean isUpdated = sectorService.updateSectorBasicInfo(sectorBasicData);
		if (isUpdated)
			return "Sector Edited";
		else
			return "Update Failed";
	}

	@RequestMapping(value = "/sector/getCompanies", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "https://stockmarketchartingfrontend.herokuapp.com/")
	public String getAllCompaniesAndFindByString(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("sectorId") == null) {
			return "sectorId must not be null";
		}
		List<Company> list = new ArrayList<>();
		Long sectorId = jsonNode.get("sectorId").asLong();

		if (jsonNode.get("search") != null) {
			String search = jsonNode.get("search").asText();
			list = companyService.findBySector_IdAndCompanyNameContaining(sectorId, search);

		} else {
			list = sectorService.getCompaniesBySectorId(sectorId);
		}
		if (list == null)
			list = new ArrayList<Company>();
		try {
			return mapper.writeValueAsString(
					JsonView.with(list).onClass(Company.class, match().exclude("*").include("id", "companyName")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/sector/remove", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "https://stockmarketchartingfrontend.herokuapp.com/")
	public String deleteSector(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("sectorId") == null) {
			return "sectorId must not be null";
		}
		Long sectorId = jsonNode.get("sectorId").asLong();
		boolean isDeleted = sectorService.deleteSector(sectorId);
		if (isDeleted)
			return "DELETED";
		else
			return "deletion Failed Please check Sector Id";
	}
}
