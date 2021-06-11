package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {

}
