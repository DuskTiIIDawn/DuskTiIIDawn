package com.example.StockMarketCharting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.StockCode;

@Repository
public interface StockCodeRepository extends JpaRepository<StockCode, Long> {

	List<StockCode> findByStockCode(Long stockCode);

}