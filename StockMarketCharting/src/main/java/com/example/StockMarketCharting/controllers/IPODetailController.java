package com.example.StockMarketCharting.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.IPODetail;
import com.example.StockMarketCharting.services.IPODetailService;

@Controller
public class IPODetailController {

	@Autowired
	IPODetailService service;

	@RequestMapping(value = "/ipoDetail", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin("*")
	public String manageCompany(@RequestBody IPODetail ipoDetail) {
		List<IPODetail> ipoList = service.findallIPO();
		return "ALL ipo Listed";
	}

	@RequestMapping(value = "/ipoDetail/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addCompany(@RequestBody IPODetail ipoDetail) {
		service.addIPODetail(ipoDetail);
		return "IPO Added";
	}

}
