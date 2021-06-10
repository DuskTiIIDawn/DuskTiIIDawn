package com.example.StockMarketCharting.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class StockExchange {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String stockExchangeName;
	
	@Column(nullable = false)
	private String brief;
	
	@Column(nullable = false)
	private String contactAddress;
	
	@Column(nullable = false)
	private String  remarks;
	
	@ManyToMany(mappedBy="stockExchanges")
	private List<Company> companies = new ArrayList<>();
	
	@ManyToMany(mappedBy="stockExchanges")
	private List<IPODetail> ipos = new ArrayList<>();
	
	@OneToMany(mappedBy="stockExchange")
	private List<StockPrice> stockPrices = new ArrayList<>();
	
	
	protected StockExchange() {}

	public StockExchange(String stockExchangeName, String brief, String contactAddress, String remarks) {
		super();
		this.stockExchangeName = stockExchangeName;
		this.brief = brief;
		this.contactAddress = contactAddress;
		this.remarks = remarks;
	}

	public Long getId() {
		return id;
	}

	public String getStockExchangeName() {
		return stockExchangeName;
	}

	public String getBrief() {
		return brief;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public String getRemarks() {
		return remarks;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public List<IPODetail> getIpos() {
		return ipos;
	}

	public List<StockPrice> getStockPrices() {
		return stockPrices;
	}

	public void setStockExchangeName(String stockExchangeName) {
		this.stockExchangeName = stockExchangeName;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public void setIpos(List<IPODetail> ipos) {
		this.ipos = ipos;
	}

	public void setStockPrices(List<StockPrice> stockPrices) {
		this.stockPrices = stockPrices;
	}

	

	
}
