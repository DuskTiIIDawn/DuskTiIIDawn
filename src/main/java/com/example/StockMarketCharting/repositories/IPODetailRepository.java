package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.IPODetail;

public interface IPODetailRepository extends JpaRepository<IPODetail, Long> {

}
