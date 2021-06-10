package com.example.StockMarketCharting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.StockMarketCharting.entities.Sector;



@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

}
