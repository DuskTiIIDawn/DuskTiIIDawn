package com.example.StockMarketCharting.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.StockPrice;
import com.example.StockMarketCharting.repositories.StockPriceRepository;

@Service
public class StockPriceService {

	@Autowired
	StockPriceRepository stockPriceRepository;

	public void addStockPrice(StockPrice stockPrice) {
		stockPriceRepository.save(stockPrice);
	}

	public List<StockPrice> findByStockCode_Id(Long stockCodeId) {
		return stockPriceRepository.findByStockCode_Id(stockCodeId);
	}

	public List<StockPrice> findByStockCode_IdAndBetweenDateTime(Long stockCodeId, LocalDateTime d1, LocalDateTime d2) {
		return stockPriceRepository.findByStockCode_IdAndDateTimeBetween(stockCodeId, d1, d2);
	}

	public Set<Date> findDatesWhereRecordExist(LocalDate d1, LocalDate d2, Long stockCodeId) {
		return stockPriceRepository.findDatesWhereRecordExist(d1, d2, stockCodeId);
	}

}
