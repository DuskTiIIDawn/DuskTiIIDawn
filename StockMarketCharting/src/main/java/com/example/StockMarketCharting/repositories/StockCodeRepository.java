package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.StockCode;

public interface StockCodeRepository extends JpaRepository<StockCode, Long> {

}