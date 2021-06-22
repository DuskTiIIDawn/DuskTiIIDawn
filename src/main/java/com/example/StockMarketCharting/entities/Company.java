package com.example.StockMarketCharting.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, ignoreUnknown = true)
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String companyName;

	@Column(nullable = false)
	private Double turnover;

	@Column(nullable = false)
	private String ceo;

	@Column(nullable = false)
	@Type(type = "text")
	private String boardOfDirectors;

	@Column(nullable = false)
	@Type(type = "text")
	private String companyBrief;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private IPODetail ipo;

	@OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<StockCode> stockCodes = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Sector sector;

	protected Company() {
	}

	public Company(String companyName, double turnover, String ceo, String boardOfDirectors, String companyBrief) {
		super();
		this.companyName = companyName;
		this.turnover = turnover;
		this.ceo = ceo;
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

	public String getCeo() {
		return ceo;
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

	public List<StockCode> getStockCodes() {
		return stockCodes;
	}

	public Sector getSector() {
		return sector;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public void setCeo(String ceo) {
		this.ceo = ceo;
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

	public void setStockCodes(List<StockCode> stockCodes) {
		this.stockCodes = stockCodes;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

}