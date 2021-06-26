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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy H:m:s"); // for reading excel
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d"); // for reading missing records
																						// date input
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m"); // for reading see data
																								// input

	@Autowired
	StockPriceService service;

	@Autowired
	StockCodeService stockCodeService;

	// AKA....import of Data
	@RequestMapping(value = "/stockPrice/upload", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Map<String, Object> addStockExchange(@RequestBody Map<String, JsonNode> requestMap) {
		Map<String, Object> response = new HashMap<>();
		int cnt = 0;
		for (String dataNo : requestMap.keySet()) {
			Long stockCodeNo = requestMap.get(dataNo).get("stockCodeNo").asLong();
			double currentPrice = requestMap.get(dataNo).get("currentPrice").asDouble();
			String dateTimeStr = requestMap.get(dataNo).get("dateAndTime").asText();
			LocalDateTime dateTime = null;

			StockCode stockCode = stockCodeService.findByStockCode(stockCodeNo);
			if (stockCode == null) {
				response.put(dataNo, "Failed Stock Code Does Not Exist in Record");
				continue;
			}

			try {
				dateTime = LocalDateTime.parse(dateTimeStr, formatter);
			} catch (Exception e) {
				response.put(dataNo, "Date Time Format Incorrect");
				continue;
			}

			List<StockPrice> stockPrices = service.findByStockCode_IdAndDateTime(stockCode.getId(), dateTime);
			if (stockPrices.size() > 0) {
				StockPrice stockPrice = stockPrices.get(0);
				stockPrice.setCurrentPrice(currentPrice);
				service.addStockPrice(stockPrice);
				response.put(dataNo, "Current Price Overwritten");
				cnt += 1;

			} else {
				StockPrice stockPrice = new StockPrice(currentPrice, dateTime);
				stockPrice.setStockCode(stockCode);
				service.addStockPrice(stockPrice);
				response.put(dataNo, "Updated Successfully");
				cnt += 1;
			}
		}
		response.put("count", cnt);
		return response;
	}

	@RequestMapping(value = "/stockPrice/getByStockCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByStockCode(@RequestBody JsonNode jsonNode) {
		Map<String, Object> response = new HashMap<>();
		if (jsonNode.get("stockCodeNo") == null) {
			response.put("stockCodeError", "Stock Code No must not be null");
			return response;
		}
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = stockCodeService.findByStockCode(stockCodeNo);
		if (stockCode == null) {
			response.put("stockCodeError", "Stock Code Does Not Exist");
			return response;
		}
		if (jsonNode.get("startDateTime") != null && jsonNode.get("endDateTime") != null) {
			String startDateTimeStr = jsonNode.get("startDateTime").asText();
			String endDateTimeStr = jsonNode.get("endDateTime").asText();
			LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, dateTimeFormatter);
			LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, dateTimeFormatter);
			List<StockPrice> stockPrices = service.findByStockCode_IdAndBetweenDateTime(stockCode.getId(),
					startDateTime, endDateTime);
			response.put("result", stockPrices);
			return response;
		}
		response.put("result", service.findByStockCode_Id(stockCode.getId()));
		return response;

	}

	@RequestMapping(value = "/stockPrice/removeByStockCode", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Long removeByStockCode(@RequestBody JsonNode jsonNode) {
		if (jsonNode.get("stockCodeNo") == null) {
			return 0L;
		}
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = stockCodeService.findByStockCode(stockCodeNo);
		if (stockCode == null) {
			return 0L;
		}
		if (jsonNode.get("startDateTime") != null && jsonNode.get("endDateTime") != null) {
			String startDateTimeStr = jsonNode.get("startDateTime").asText();
			String endDateTimeStr = jsonNode.get("endDateTime").asText();
			LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, dateTimeFormatter);
			LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, dateTimeFormatter);
			return service.deleteByStockCode_IdAndBetweenDateTime(stockCode.getId(), startDateTime, endDateTime);
		}
		return service.deleteByStockCode_Id(stockCode.getId());

	}

	@RequestMapping(value = "/stockPrice/missingRecords", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Map<String, Object> getMissingRecordsByStockCode(@RequestBody JsonNode jsonNode) {
		Map<String, Object> response = new HashMap<>();
		List<LocalDate> listOfDatesMissing = new ArrayList<>();
		if (jsonNode.get("stockCodeNo") == null) {
			response.put("stockCodeError", "Stock Code Id must not be null");
			return response;
		}
		Long stockCodeNo = jsonNode.get("stockCodeNo").asLong();
		StockCode stockCode = stockCodeService.findByStockCode(stockCodeNo);
		if (stockCode == null) {
			response.put("stockCodeError", "Stock Code Does Not Exist");
			return response;
		}

		Long stockCodeId = stockCode.getId();
		if (jsonNode.get("startDate") != null && jsonNode.get("endDate") != null) {
			String startDateTimeStr = jsonNode.get("startDate").asText();
			String endDateTimeStr = jsonNode.get("endDate").asText();
			LocalDate startDate = LocalDate.parse(startDateTimeStr, dateFormatter);
			LocalDate endDate = LocalDate.parse(endDateTimeStr, dateFormatter);
			Set<Date> setOfDatesExist = service.findDatesWhereRecordExist(startDate, endDate, stockCodeId);
			while (!startDate.isAfter(endDate)) {
				if (!setOfDatesExist.contains(Date.valueOf(startDate))) {
					listOfDatesMissing.add(startDate);
				}
				startDate = startDate.plusDays(1);
			}
			response.put("result", listOfDatesMissing);
			return response;
		}
		response.put("dateError", "Date Input Not Provided");
		return response;

	}

}
