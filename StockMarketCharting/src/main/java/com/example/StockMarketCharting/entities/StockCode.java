package com.example.StockMarketCharting.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StockCode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "stock_exchange_id")
	private StockExchange stockExchange;

	@Column(name = "stock_code")
	private Long stockCode;

	public Company getCompany() {
		return company;
	}

	protected StockCode() {
	}

	public StockCode(Company company, StockExchange stockExchange, Long stockCode) {
		super();
		this.company = company;
		this.stockExchange = stockExchange;
		this.stockCode = stockCode;
	}

	public StockExchange getStockExchange() {
		return stockExchange;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}

	public void setStockCode(Long stockCode) {
		this.stockCode = stockCode;
	}

	public Long getId() {
		return id;
	}

	public Long getStockCode() {
		return stockCode;
	}

}
