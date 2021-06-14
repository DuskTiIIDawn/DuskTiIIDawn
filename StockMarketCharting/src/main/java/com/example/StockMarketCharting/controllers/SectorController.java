package com.example.StockMarketCharting.controllers;

import static com.monitorjbl.json.Match.match;

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

	@RequestMapping(value = "/sector", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin("*")
	public List<Sector> getAll() {
		return sectorService.findAll();
	}

	@RequestMapping(value = "/sector/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addSector(@RequestBody Sector sector) {
		sectorService.addSector(sector);
		return "Sector Added";
	}

	@RequestMapping(value = "/sector/getCompanies", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String getAllCompanies(@RequestBody JsonNode jsonNode) {
		Long sectorId = jsonNode.get("sectorId").asLong();
		List<Company> companies = sectorService.getCompanies(sectorId);

		String json = "";
		try {
			json = mapper.writeValueAsString(
					JsonView.with(companies).onClass(Company.class, match().exclude("*").include("id", "companyName")));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;

	}

}
