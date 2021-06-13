package com.example.StockMarketCharting.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.Sector;
import com.example.StockMarketCharting.repositories.SectorRepository;

@Service
public class SectorService {

	@Autowired
	SectorRepository sectorRepository;

	public void addSector(Sector sector) {
		sectorRepository.save(sector);
	}

	public Sector findById(long l) {
		Optional<Sector> ose = sectorRepository.findById(l);
		if (ose.isPresent()) {
			return ose.get();
		} else {
			return null;
		}
	}

}
