package com.example.StockMarketCharting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.Sector;
import com.example.StockMarketCharting.services.SectorService;

@Controller
public class SectorController {

	@Autowired
	SectorService sectorService;

	@RequestMapping(value = "/sector/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addSector(@RequestBody Sector sector) {
		sectorService.addSector(sector);
		return "Sector Added";
	}

}
