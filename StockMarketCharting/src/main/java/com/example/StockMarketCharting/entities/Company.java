package com.example.StockMarketCharting.entities;

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

	@ManyToMany
	@JoinTable(name = "COMPANY_STOCK_EXCHANGE", joinColumns = @JoinColumn(name = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "STOCK_EXCHANGE_ID"))
	private List<StockExchange> stockExchanges = new ArrayList<>();

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
		this.CEO = cEO;
		this.boardOfDirectors = boardOfDirectors;
		this.companyBrief = companyBrief;
	}

	public Long getId() {
		return id;

	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public String getCEO() {
		return CEO;
	}

	public void setCEO(String cEO) {
		this.CEO = cEO;
	}

	public String getBoardOfDirectors() {
		return boardOfDirectors;
	}

	public void setBoardOfDirectors(String boardOfDirectors) {
		this.boardOfDirectors = boardOfDirectors;
	}

	public IPODetail getIpo() {
		return ipo;
	}

	public void setIpo(IPODetail ipo) {
		this.ipo = ipo;
	}

	public List<StockExchange> getStockExchanges() {
		return stockExchanges;
	}

	public void setStockExchanges(List<StockExchange> stockExchanges) {
		this.stockExchanges = stockExchanges;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public List<StockPrice> getStockPrices() {
		return stockPrices;
	}

	public void setStockPrices(List<StockPrice> stockPrices) {
		this.stockPrices = stockPrices;
	}

	public String getCompanyBrief() {
		return companyBrief;
	}

	public void setCompanyBrief(String companyBrief) {
		this.companyBrief = companyBrief;
	}

}
