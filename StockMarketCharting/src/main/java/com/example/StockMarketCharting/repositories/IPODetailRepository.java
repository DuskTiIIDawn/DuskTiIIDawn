package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.IPODetail;

@Repository
public interface IPODetailRepository extends JpaRepository<IPODetail, Long> {

}
