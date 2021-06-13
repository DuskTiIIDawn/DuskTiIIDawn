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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class StockExchange {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String stockExchangeName;

	@Column(nullable = false)
	private String brief;

	@Column(nullable = false)
	private String contactAddress;

	@Column(nullable = false)
	private String remarks;

	@ManyToMany(mappedBy = "stockExchanges")
	@JsonIgnore
	private List<IPODetail> ipos = new ArrayList<>();

	@OneToMany(mappedBy = "stockExchange")
	@JsonIgnore
	private List<StockCode> stockCodes = new ArrayList<>();

	protected StockExchange() {
	}

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

	public List<IPODetail> getIpos() {
		return ipos;
	}

	public List<StockCode> getStockCodes() {
		return stockCodes;
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

	public void setIpos(List<IPODetail> ipos) {
		this.ipos = ipos;
	}

	public void setStockCodes(List<StockCode> stockCodes) {
		this.stockCodes = stockCodes;
	}

}
