package com.example.StockMarketCharting.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Sector {
  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String sectorName;
	
	@Column(nullable = false)
	private String brief;
	
	@OneToMany(mappedBy="sector")
	private List<Company> companies = new ArrayList<>();

	public Long getId() {
		return id;
	}
	
	protected Sector() {}
	
	

	public Sector(String sectorName, String brief, List<Company> companies) {
		super();
		this.sectorName = sectorName;
		this.brief = brief;
		this.companies = companies;
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
