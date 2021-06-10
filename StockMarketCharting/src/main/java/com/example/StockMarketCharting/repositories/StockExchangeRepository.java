package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.StockExchange;


@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {

}
