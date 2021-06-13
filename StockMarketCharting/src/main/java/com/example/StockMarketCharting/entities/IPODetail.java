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

@Entity
public class IPODetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private double pricePerShare;

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

}
