package com.example.StockMarketCharting.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.Company;
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

	public List<Sector> findAll() {
		return sectorRepository.findAll();
	}

	public List<Company> getCompanies(Long sectorId) {
		Sector sector = this.findById(sectorId);
		if (sector != null) {
			return sector.getCompanies();
		} else
			return null;
	}

}
