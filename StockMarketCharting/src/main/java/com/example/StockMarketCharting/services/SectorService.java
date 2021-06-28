package com.example.StockMarketCharting.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
		return sectorRepository.findAllByOrderBySectorName();
	}

	public List<Company> getCompaniesBySectorId(Long sectorId) {
		Sector sector = this.findById(sectorId);
		if (sector != null) {
			return sector.getCompanies();
		} else
			return null;
	}

	public boolean updateSectorBasicInfo(Sector sectorBasicData) {
		Long id = (Long) sectorBasicData.getId();
		if (id == null)
			return false;
		Optional<Sector> os = sectorRepository.findById(id);
		if (os.isPresent()) {
			Sector dataRepoSector = os.get();
			dataRepoSector.setSectorName(sectorBasicData.getSectorName());
			dataRepoSector.setBrief(sectorBasicData.getBrief());
			sectorRepository.save(dataRepoSector);
			return true;
		} else {
			return false;
		}

	}

	@Transactional
	public boolean deleteSector(Long id) {
		Sector sector = this.findById(id);
		if (sector == null) {
			return false;
		} else {
			List<Company> companies = sector.getCompanies();
			for (Company company : companies)
				company.setSector(null);
			sectorRepository.deleteById(id);
			return true;
		}

	}

}
