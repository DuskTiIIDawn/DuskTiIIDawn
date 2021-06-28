package com.example.StockMarketCharting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StockMarketCharting.entities.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {
	List<Sector> findAllByOrderBySectorName();

}
