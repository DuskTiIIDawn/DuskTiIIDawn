package com.example.StockMarketCharting.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.StockMarketCharting.services.UserEntityService;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	UserEntityService service;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.example.StockMarketCharting.entities.UserEntity user = service.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				new ArrayList<>());
	}

	public com.example.StockMarketCharting.entities.UserEntity save(
			com.example.StockMarketCharting.entities.UserEntity user) {
		com.example.StockMarketCharting.entities.UserEntity newUser = new com.example.StockMarketCharting.entities.UserEntity();
		newUser.setUserName(user.getUserName());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return service.saveUser(newUser);
	}

}
