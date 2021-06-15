package com.example.StockMarketCharting.controllers;

import static com.monitorjbl.json.Match.match;

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

import com.example.StockMarketCharting.entities.Company;
import com.example.StockMarketCharting.entities.IPODetail;
import com.example.StockMarketCharting.entities.StockExchange;
import com.example.StockMarketCharting.services.IPODetailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewModule;

@Controller
public class IPODetailController {
	private ObjectMapper mapper = new ObjectMapper().registerModules(new JsonViewModule(), new JavaTimeModule());

	@Autowired
	IPODetailService service;

	@RequestMapping(value = "/ipoDetail", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin("*")
	public String getAllIpo() {
		List<IPODetail> ipoList = service.findallIPO();

		try {
			return mapper.writeValueAsString(JsonView.with(ipoList).onClass(IPODetail.class, match().include("company"))
					.onClass(Company.class, match().exclude("*").include("companyName", "id")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/ipoDetail/info", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String getIpoDetail(@RequestBody JsonNode requestMap) {
		if (requestMap.get("ipoDetailId") == null) {
			return "ipoDetailId must not be null";
		}
		Long ipoDetailId = requestMap.get("ipoDetailId").asLong();
		IPODetail ipoDetail = service.findById(ipoDetailId);
		if (ipoDetail == null) {
			return "IPO does Not Exist";
		}

		try {
			return mapper.writeValueAsString(
					JsonView.with(ipoDetail).onClass(IPODetail.class, match().include("company", "stockExchanges"))
							.onClass(Company.class, match().exclude("*").include("companyName", "id"))
							.onClass(StockExchange.class, match().exclude("*").include("stockExchangeName", "id")));
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/ipoDetail/addUpdate", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public String addOrUpdateIPO(@RequestBody JsonNode requestMap) {

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
			return service.addIPO(companyId, stockExchangeIds, ipoDetail);

		} else {
			Long ipoDetailId = requestMap.get("ipoDetailId").asLong();
			return service.UpdateIPO(companyId, stockExchangeIds, ipoDetail, ipoDetailId);
		}

	}

	@RequestMapping(value = "/ipoDetail/remove", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public boolean removeIPO(@RequestBody JsonNode requestMap) {
		if (requestMap.get("ipoDetailId") == null)
			return false;
		Long ipoDetailId = requestMap.get("ipoDetailId").asLong();
		return service.removeIPO(ipoDetailId);

	}

}
