package com.example.StockMarketCharting.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.entities.IPODetail;
import com.example.StockMarketCharting.repositories.IPODetailRepository;

@Service
public class IPODetailService {

	@Autowired
	IPODetailRepository ipoDetailRepository;

	public void addIPODetail(IPODetail ipoDetail) {
		ipoDetailRepository.save(ipoDetail);
	}

	public List<IPODetail> findallIPO() {
		List<IPODetail> ipoList = ipoDetailRepository.findAll();
		return ipoList;
	}

}
