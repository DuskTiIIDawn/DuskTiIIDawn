package com.example.StockMarketCharting.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.StockMarketCharting.entities.StockCode;
import com.example.StockMarketCharting.entities.StockPrice;
import com.example.StockMarketCharting.services.StockCodeService;
import com.example.StockMarketCharting.services.StockPriceService;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class StockPriceController {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy H:m:s");

	@Autowired
	StockPriceService service;

	@Autowired
	StockCodeService stockCodeService;

	// AKA....import of Data
	@RequestMapping(value = "/stockPrice/upload", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public Map<String, Object> addStockExchange(@RequestBody Map<String, JsonNode> requestMap) {
		Map<String, Object> response = new HashMap<>();
		int cnt = 0;
		for (String dataNo : requestMap.keySet()) {
			Long stockCodeNo = requestMap.get(dataNo).get("stockCodeNo").asLong();
			double currentPrice = requestMap.get(dataNo).get("currentPrice").asDouble();
			String dateTimeStr = requestMap.get(dataNo).get("dateAndTime").asText();
			LocalDateTime dateTime = null;

			try {
				dateTime = LocalDateTime.parse(dateTimeStr, formatter);
			} catch (Exception e) {
				response.put(dataNo, "Date Time Format Incorrect");
				continue;
			}

			StockCode stockCode = stockCodeService.findByStockCode(stockCodeNo);
			if (stockCode != null) {
				StockPrice stockPrice = new StockPrice(currentPrice, dateTime);
				stockPrice.setStockCode(stockCode);
				service.addStockPrice(stockPrice);
				response.put(dataNo, "Updated Successfully");
				cnt += 1;
			} else {
				response.put(dataNo, "Failed Stock Code Does Not Exist in Record");
			}

		}
		response.put("count", cnt);
		return response;
	}

	@RequestMapping(value = "/stockPrice/getByStockCode", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public List<StockPrice> getByStockCode(@RequestBody JsonNode jsonNode) {
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		Long stockCodeId = stockCodeService.findByStockCode(stockCodeNo).getId();
		if (jsonNode.get("startDateTime") != null && jsonNode.get("endDateTime") != null) {
			String startDateTimeStr = jsonNode.get("startDateTime").asText();
			String endDateTimeStr = jsonNode.get("endDateTime").asText();
			LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, formatter);
			LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, formatter);
			return service.findByStockCode_IdAndBetweenDateTime(stockCodeId, startDateTime, endDateTime);

		}
		return service.findByStockCode_Id(stockCodeId);

	}

	@RequestMapping(value = "/stockPrice/missingRecords", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin("*")
	public List<LocalDate> getMissingRecordsByStockCode(@RequestBody JsonNode jsonNode) {
		List<LocalDate> listOfDatesMissing = new ArrayList<>();
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = stockCodeService.findByStockCode(stockCodeNo);
		if (stockCode == null)
			return listOfDatesMissing;
		Long stockCodeId = stockCode.getId();
		if (jsonNode.get("startDateTime") != null && jsonNode.get("endDateTime") != null) {
			String startDateTimeStr = jsonNode.get("startDateTime").asText();
			String endDateTimeStr = jsonNode.get("endDateTime").asText();
			LocalDate startDate = LocalDateTime.parse(startDateTimeStr, formatter).toLocalDate();
			LocalDate endDate = LocalDateTime.parse(endDateTimeStr, formatter).toLocalDate();
			Set<Date> setOfDatesExist = service.findDatesWhereRecordExist(startDate, endDate, stockCodeId);
			while (!startDate.isAfter(endDate)) {
				if (!setOfDatesExist.contains(Date.valueOf(startDate))) {
					listOfDatesMissing.add(startDate);
				}
				startDate = startDate.plusDays(1);
			}
			return listOfDatesMissing;
		}
		return listOfDatesMissing;

	}

}
