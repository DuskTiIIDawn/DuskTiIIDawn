package com.example.StockMarketCharting.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, ignoreUnknown = true)
public class Sector {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String sectorName;

	@Column(nullable = false)
	private String brief;

	@OneToMany(mappedBy = "sector")
	@JsonIgnore
	private List<Company> companies = new ArrayList<>();

	protected Sector() {
	}

	public Sector(String sectorName, String brief) {
		super();
		this.sectorName = sectorName;
		this.brief = brief;
	}

	public Long getId() {
		return id;
	}

	public String getSectorName() {
		return sectorName;
	}

	public String getBrief() {
		return brief;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

}
