package com.example.StockMarketCharting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.repositories.SectorRepository;

@Service
public class SectorService {

	@Autowired
	SectorRepository sectorRepository;

}
