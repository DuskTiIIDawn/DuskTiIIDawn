package com.example.StockMarketCharting.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class IPODetailController {

	@Autowired
	IPODetailService service;

	@RequestMapping(value = "/ipoDetail", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin("*")
	public List<IPODetail> getAllIpo() {
		List<IPODetail> ipoList = service.findallIPO();
		return ipoList;
	}

	@RequestMapping(value = "/ipoDetail/info", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public IPODetail getIpoDetail(@RequestBody JsonNode requestMap) {
		Long ipoDetailId = requestMap.get("ipoDetailId").asLong();
		IPODetail ipoDetail = service.findById(ipoDetailId);
		return ipoDetail;
	}

	@RequestMapping(value = "/ipoDetail/addUpdate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public boolean addIPO(@RequestBody JsonNode requestMap) {
		boolean response = false;
		Double pricePerShare = requestMap.get("pricePerShare").asDouble();
		Long totalNoOfShares = requestMap.get("totalNoOfShares").asLong();
		String openDateTimestr = requestMap.get("openDateTime").asText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime openDateTime = LocalDateTime.parse(openDateTimestr, formatter);
		Long companyId = requestMap.get("companyId").asLong();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
		List<Long> stockExchangeIds = objectMapper.convertValue(requestMap.get("stockExchangeIds"), ArrayList.class);

		IPODetail ipoDetail = new IPODetail(pricePerShare, totalNoOfShares, openDateTime);

		if (requestMap.get("ipoDetailId") == null) {
			response = service.addIPO(companyId, stockExchangeIds, ipoDetail);

		} else {
			Long ipoDetailId = requestMap.get("ipoDetailId").asLong();
			response = service.UpdateIPO(companyId, stockExchangeIds, ipoDetail, ipoDetailId);
		}

		return response;
	}

	@RequestMapping(value = "/ipoDetail/remove", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public boolean removeIPO(@RequestBody JsonNode requestMap) {
		Long ipoDetailId = requestMap.get("ipoDetailId").asLong();
		return service.removeIPO(ipoDetailId);

	}

}
