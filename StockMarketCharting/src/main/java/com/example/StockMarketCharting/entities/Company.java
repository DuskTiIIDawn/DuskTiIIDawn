package com.example.StockMarketCharting.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String companyName;

	@Column(nullable = false)
	private double turnover;

	@Column(nullable = false)
	@JsonProperty("ceo")
	private String CEO;

	@Column(nullable = false)
	@Type(type = "text")
	private String boardOfDirectors;

	@Column(nullable = false)
	@Type(type = "text")
	private String companyBrief;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "company")
	private IPODetail ipo;

	@OneToMany(mappedBy = "company")
	private List<StockCode> stockExchageCodes = new ArrayList<>();

	@ManyToOne
	private Sector sector;

	@OneToMany(mappedBy = "company")
	private List<StockPrice> stockPrices = new ArrayList<>();

	protected Company() {
	}

	public Company(String companyName, double turnover, String cEO, String boardOfDirectors, String companyBrief) {
		super();
		this.companyName = companyName;
		this.turnover = turnover;
		CEO = cEO;
		this.boardOfDirectors = boardOfDirectors;
		this.companyBrief = companyBrief;
	}

	public Long getId() {
		return id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public double getTurnover() {
		return turnover;
	}

	public String getCEO() {
		return CEO;
	}

	public String getBoardOfDirectors() {
		return boardOfDirectors;
	}

	public String getCompanyBrief() {
		return companyBrief;
	}

	public IPODetail getIpo() {
		return ipo;
	}

	public List<StockCode> getStockExchageCodes() {
		return stockExchageCodes;
	}

	public Sector getSector() {
		return sector;
	}

	public List<StockPrice> getStockPrices() {
		return stockPrices;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public void setCEO(String cEO) {
		CEO = cEO;
	}

	public void setBoardOfDirectors(String boardOfDirectors) {
		this.boardOfDirectors = boardOfDirectors;
	}

	public void setCompanyBrief(String companyBrief) {
		this.companyBrief = companyBrief;
	}

	public void setIpo(IPODetail ipo) {
		this.ipo = ipo;
	}

	public void setStockExchageCodes(List<StockCode> stockExchageCodes) {
		this.stockExchageCodes = stockExchageCodes;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public void setStockPrices(List<StockPrice> stockPrices) {
		this.stockPrices = stockPrices;
	}

}