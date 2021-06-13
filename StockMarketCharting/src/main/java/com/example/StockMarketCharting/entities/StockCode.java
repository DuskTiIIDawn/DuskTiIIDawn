package com.example.StockMarketCharting.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class StockCode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@JsonIgnore
	private Company company;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "stock_exchange_id")
	private StockExchange stockExchange;

	@OneToMany(mappedBy = "stockCode", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<StockPrice> stockPrices;

	@Column(name = "stock_code", nullable = false)
	private Long stockCode;

	protected StockCode() {
	}

	public StockCode(Long stockCode) {
		super();
		this.stockCode = stockCode;
	}

	public Long getId() {
		return id;
	}

	public Company getCompany() {
		return company;
	}

	public StockExchange getStockExchange() {
		return stockExchange;
	}

	public List<StockPrice> getStockPrices() {
		return stockPrices;
	}

	public Long getStockCode() {
		return stockCode;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}

	public void setStockPrices(List<StockPrice> stockPrices) {
		this.stockPrices = stockPrices;
	}

	public void setStockCode(Long stockCode) {
		this.stockCode = stockCode;
	}

}
