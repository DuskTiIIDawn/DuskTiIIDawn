package com.example.StockMarketCharting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.StockExchange;

public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {
	List<StockExchange> findAllByOrderByStockExchangeName();

}
