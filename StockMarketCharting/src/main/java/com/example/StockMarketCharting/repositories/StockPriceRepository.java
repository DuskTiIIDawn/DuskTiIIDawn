package com.example.StockMarketCharting.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.StockPrice;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {

	List<StockPrice> findByStockCode_Id(Long stockCodeId);

	List<StockPrice> findByStockCode_IdAndDateTimeBetween(Long stockCodeId, LocalDateTime d1, LocalDateTime d2);

}
