package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.StockExchange;

public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {

}
