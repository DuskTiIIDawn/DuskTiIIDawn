package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.StockPrice;


@Repository
public interface StockPriceRepository  extends JpaRepository<StockPrice, Long>{

}
