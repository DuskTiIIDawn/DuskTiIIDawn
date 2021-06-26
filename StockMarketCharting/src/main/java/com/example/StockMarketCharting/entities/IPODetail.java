package com.example.StockMarketCharting.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, ignoreUnknown = true)
public class IPODetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Double pricePerShare;

	@Column(nullable = false)
	private Long totalNumberOfShares;

	private LocalDateTime openDateTime;

	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Company company;

	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "IPO_STOCK_EXCHANGE", joinColumns = @JoinColumn(name = "IPO_ID"), inverseJoinColumns = @JoinColumn(name = "STOCK_EXCHANGE_ID"))
	private List<StockExchange> stockExchanges = new ArrayList<>();

	protected IPODetail() {
	}

	public IPODetail(double pricePerShare, Long totalNumberOfShares, LocalDateTime openDateTime) {
		super();
		this.pricePerShare = pricePerShare;
		this.totalNumberOfShares = totalNumberOfShares;
		this.openDateTime = openDateTime;
	}

	public Long getId() {
		return id;
	}

	public double getPricePerShare() {
		return pricePerShare;
	}

	public Long getTotalNumberOfShares() {
		return totalNumberOfShares;
	}

	public LocalDateTime getOpenDateTime() {
		return openDateTime;
	}

	public Company getCompany() {
		return company;
	}

	public List<StockExchange> getStockExchanges() {
		return stockExchanges;
	}

	public void setPricePerShare(double pricePerShare) {
		this.pricePerShare = pricePerShare;
	}

	public void setTotalNumberOfShares(Long totalNumberOfShares) {
		this.totalNumberOfShares = totalNumberOfShares;
	}

	public void setOpenDateTime(LocalDateTime openDateTime) {
		this.openDateTime = openDateTime;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setStockExchanges(List<StockExchange> stockExchanges) {
		this.stockExchanges = stockExchanges;
	}

}
