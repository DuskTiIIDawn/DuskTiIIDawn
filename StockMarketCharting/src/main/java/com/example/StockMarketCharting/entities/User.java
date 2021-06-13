package com.example.StockMarketCharting.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean isAdmin;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String mobileNumber;

	@Column(nullable = false)
	private boolean isConfirmed;

	protected User() {
	}

}
