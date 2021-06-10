package com.example.StockMarketCharting.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class StockPrice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long ID;

	@ManyToOne
	private Company company ;
	
	@ManyToOne
	private StockExchange stockExchange ;
	
	
	@Column(nullable = false)
	private double currentPrice;
	
	@Column(nullable = false)
	private LocalDateTime dateTime;

	public StockPrice(Company company, StockExchange stockExchage, double currentPrice, LocalDateTime dateTime) {
		super();
		this.company = company;
		this.stockExchange = stockExchage;
		this.currentPrice = currentPrice;
		this.dateTime = dateTime;
	}

	public Long getID() {
		return ID;
	}

	public Company getCompany() {
		return company;
	}

	public StockExchange getStockExchage() {
		return stockExchange;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setStockExchage(StockExchange stockExchage) {
		this.stockExchange = stockExchage;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	
	
	
}
