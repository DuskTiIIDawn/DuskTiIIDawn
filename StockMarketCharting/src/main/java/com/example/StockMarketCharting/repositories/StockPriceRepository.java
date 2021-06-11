package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.StockPrice;

public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {

}
