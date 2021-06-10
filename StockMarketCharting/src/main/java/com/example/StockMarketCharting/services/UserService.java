package com.example.StockMarketCharting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

}
