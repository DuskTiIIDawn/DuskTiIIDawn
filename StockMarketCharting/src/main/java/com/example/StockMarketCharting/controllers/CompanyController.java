package com.example.StockMarketCharting.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.services.CompanyService;

@Controller
public class CompanyController {

	@Autowired
	CompanyService service;

	@RequestMapping(value = "/manageCompany", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin("*")
	public String manageCompany(@RequestBody Company company) {
		List<Company> companyList = service.findallCompanies();
		return "ALL companies Listed";
	}

	@RequestMapping(value = "/manageCompany/add", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addCompany(@RequestBody Company company) {
		service.addCompany(company);
		return "Company Added";
	}

	@RequestMapping(value = "/manageCompany/edit", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String updateCompany(@RequestBody Company company) {
		boolean isUpdated = service.updateCompany(company);
		if (isUpdated) {
			return "Company Updated";
		} else {
			return "Update Failed";
		}
	}

}
