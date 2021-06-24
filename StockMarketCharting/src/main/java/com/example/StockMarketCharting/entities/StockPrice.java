package com.example.StockMarketCharting.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, ignoreUnknown = true)
public class StockPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private StockCode stockCode;

	@Column(nullable = false)
	private Double currentPrice;

	@Column(nullable = false)
	private LocalDateTime dateTime;

	protected StockPrice() {
	}

	public StockPrice(double currentPrice, LocalDateTime dateTime) {
		super();
		this.currentPrice = currentPrice;
		this.dateTime = dateTime;
	}

	public Long getId() {
		return id;
	}

	public StockCode getStockCode() {
		return stockCode;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setStockCode(StockCode stockCode) {
		this.stockCode = stockCode;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

}
